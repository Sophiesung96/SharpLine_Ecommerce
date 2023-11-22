package com.example.demo01.src.Controller;

import com.example.demo01.src.main.Configuration.MailConfiguration;
import com.example.springboot_ecommerce.Exception.CustomerNotFoundException;
import com.example.springboot_ecommerce.Pojo.CartItem;
import com.example.springboot_ecommerce.Pojo.CartItemPName;
import com.example.springboot_ecommerce.Pojo.Customer;
import com.example.springboot_ecommerce.Service.CustomerService;
import com.example.springboot_ecommerce.Service.ShoppingCartService;
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
        if(list!=null||cartItemPNameList!=null){
            for(CartItem cartItem:list){
                cartItem.setList(cartItemPNameList);

            }
        }        model.addAttribute("cartItemlist",list);
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
