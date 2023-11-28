package com.example.demo01.src.Security;

import com.example.demo01.src.Pojo.AuthenticationType;
import com.example.demo01.src.Pojo.Customer;
import com.example.demo01.src.Service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class DatabaseLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Autowired
    CustomerService customerService;

    private static final Logger log= LoggerFactory.getLogger(DatabaseLoginSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        CustomUserDetail customUserDetail = new CustomUserDetail(
                userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );
        String name= customUserDetail.getUsername();
        String fullName[]=name.split(" ");
        Customer customer=customerService.findByCustomerName(fullName[0]);
       log.info("name:{},password:{}",name,userDetails.getPassword());
       customerService.updateAuthenticationType(customer, AuthenticationType.DATABASE);
        //once the user is authenticated by customerDao, he will be led to the /index
            response.sendRedirect("/index");
        super.onAuthenticationSuccess(request, response, authentication);

    }
}
