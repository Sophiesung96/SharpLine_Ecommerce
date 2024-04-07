package com.example.demo01.src.Configuration.Utils;

import com.example.demo01.src.Configuration.MailConfiguration;
import com.example.demo01.src.Exception.CustomerNotFoundException;
import com.example.demo01.src.Pojo.Customer;
import com.example.demo01.src.Service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@Slf4j
public class ControllerHelper {
    @Autowired
    CustomerService customerService;

    public Customer getAuthenticatedCustomer(HttpServletRequest request) {
        String email = MailConfiguration.getEmailOfAuthenticatedCustomer(request);
        if (email == null) {
            throw new CustomerNotFoundException("No Aunthenticated Customer");
        }
        if (customerService.getCustomerByEmail(email) != null) {
            return customerService.getCustomerByEmail(email);

        } else {
            String userName = email;
            Customer customer = customerService.getCustomerByfullName(userName);
            return customer;

        }
    }

    public Customer getAuthenticatedCustomerForReviewVote(HttpServletRequest request){
        String email = MailConfiguration.getEmailOfAuthenticatedCustomer(request);
        if (customerService.getCustomerByEmail(email) != null) {
            return customerService.getCustomerByEmail(email);

        } else if(customerService.getCustomerByEmail(email) == null){
            String userName = email;
            if(userName!=null && !userName.isEmpty()){
                Customer customer = customerService.getCustomerByfullName(userName);
                log.info("customer name is not null:{}",customer!=null);
                return customer;
            }
            return null;
        }else{
            return null;
        }

    }
}
