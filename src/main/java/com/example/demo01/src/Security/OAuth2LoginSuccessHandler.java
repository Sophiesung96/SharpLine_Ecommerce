package com.example.demo01.src.Security;

import com.example.springboot_ecommerce.Pojo.AuthenticationType;
import com.example.springboot_ecommerce.Pojo.Customer;
import com.example.springboot_ecommerce.Service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
//this class is to update or renew customer info who logged in by Google
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

  private static final Logger log=LoggerFactory.getLogger(OAuth2LoginSuccessHandler.class);

  @Autowired
    CustomerService customerService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
        CustomerOAuth2User customerOAuth2User = new CustomerOAuth2User(oidcUser);
        String name=customerOAuth2User.getName();
       String email=customerOAuth2User.getEmail();
       //get country name  of the user's location
       String countryCode=request.getLocale().getCountry();
       log.info("OAuth2LoginSuccessHandler:name:{},email:{},country:{}",name,email,countryCode);

     Customer customer=customerService.getCustomerByEmail(email);
          if(customer==null){
           //create new customer into database
             customerService.addUponOAuth2Login(email,name,countryCode);
           }else{
             if(customer.getAuthenticationType()==null){
               customer.setAuthenticationType("GOOGLE");
         }
               customerService.updateAuthenticationType(customer, AuthenticationType.GOOGLE);}
        response.sendRedirect("/index");
          //once the user is authenticated by google, he will be led to the /index
        super.onAuthenticationSuccess(request, response, authentication);


    }


}
