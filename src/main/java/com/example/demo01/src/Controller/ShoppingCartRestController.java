package com.example.demo01.src.Controller;

import com.example.demo01.src.main.Configuration.MailConfiguration;
import com.example.springboot_ecommerce.Exception.CustomerNotFoundException;
import com.example.springboot_ecommerce.Exception.ShoppingCartException;
import com.example.springboot_ecommerce.Pojo.Customer;
import com.example.springboot_ecommerce.Service.CustomerService;
import com.example.springboot_ecommerce.Service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class ShoppingCartRestController {
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private CustomerService customerService;

    @PostMapping("/cart/add/{productId}/{quantity}")
    public String addProducttoCart(@PathVariable int productId,@PathVariable int quantity,HttpServletRequest request){
        try{
            Customer customer=getAuthenticatedCustomer(request);
          int updatedQuantity=shoppingCartService.addProduct(productId,quantity,customer);
          return updatedQuantity+"item(s) of this product were added to your shopping cart.";
        }
        catch(CustomerNotFoundException exception){
            return "You must login to add this product!";
        }
        catch(ShoppingCartException exception){
            return exception.getMessage();
        }
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
