package com.example.demo01.src.Controller;


import com.example.demo01.src.Configuration.MailConfiguration;
import com.example.demo01.src.Exception.CustomerNotFoundException;
import com.example.demo01.src.Exception.ShoppingCartException;
import com.example.demo01.src.Pojo.Customer;
import com.example.demo01.src.Service.CustomerService;
import com.example.demo01.src.Service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/cart/update/{productId}/{quantity}")
    public String updateQuantity(@PathVariable int productId,@PathVariable int quantity,HttpServletRequest request){
        try{
            Customer customer=getAuthenticatedCustomer(request);
            float subtotal=shoppingCartService.updateQuantity(productId,quantity,customer);
            return String.valueOf(subtotal);
        }
        catch(CustomerNotFoundException exception){
            return "You must login to change quantity of product!";
        }
    }

    @DeleteMapping("/cart/remove/{productId}")
    public String RemoveProduct( @PathVariable int productId,HttpServletRequest request){
        try{
            Customer customer=getAuthenticatedCustomer(request);
            shoppingCartService.removeProduct(customer.getId(),productId);
            return "The product has been removed from your shopping cart";
        }
        catch(CustomerNotFoundException exception){
            return "You must login to remove product";
        }

    }

}
