package com.example.demo01.src.Controller;

import com.example.demo01.src.Configuration.MailConfiguration;
import com.example.demo01.src.Exception.CustomerNotFoundException;
import com.example.demo01.src.Pojo.CartItem;
import com.example.demo01.src.Pojo.CartItemPName;
import com.example.demo01.src.Pojo.Customer;
import com.example.demo01.src.Service.CustomerService;
import com.example.demo01.src.Service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@Slf4j
public class ShoppingCartController {
    @Autowired
    ShoppingCartService shoppingCartService;
    @Autowired
    CustomerService customerService;

    @GetMapping("/cart")
    public String ViewCart(Model model, HttpServletRequest request){
        Customer customer=getAuthenticatedCustomer(request);
        List<CartItem> list=shoppingCartService.listAllCartItem(customer);
        List<CartItemPName>cartItemPNameList=shoppingCartService.getJoinedProductnCustomer(customer);
        float estimatedTotal=0.0f;
        if(list!=null||cartItemPNameList!=null){
            for(CartItem cartItem:list){
                cartItem.setList(cartItemPNameList);
            }
            for(CartItemPName detail:cartItemPNameList){
               estimatedTotal+= detail.getSubTotal();
            }
        }
        model.addAttribute("cartItemlist",list);
        model.addAttribute("EstimatedTotal",estimatedTotal);
        return "Shopping_cart";
    }



    private Customer getAuthenticatedCustomer(HttpServletRequest request){
        String email = MailConfiguration.getEmailOfAuthenticatedCustomer(request);
        if (email == null) {
            throw new CustomerNotFoundException("No Aunthenticated Customer");
        }
        if (customerService.getCustomerByEmail(email) != null) {
            return customerService.getCustomerByEmail(email);

        } else {
            String userName = email;
            Customer customer = customerService.getCustomerByfullName(userName);
            log.info("Authenticated Customer for accessing shopping cart ",customer.getFirstName());
            return customer;

        }
    }
}
