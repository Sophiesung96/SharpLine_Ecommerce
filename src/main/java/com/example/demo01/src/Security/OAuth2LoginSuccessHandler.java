package com.example.demo01.src.Security;

import com.example.demo01.src.Configuration.JWTUtil;
import com.example.demo01.src.Pojo.AuthenticationType;
import com.example.demo01.src.Pojo.Customer;
import com.example.demo01.src.Service.CustomerService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
//this class is to update or renew customer info who logged in by Google
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {



  @Autowired
    CustomerService customerService;

  @Autowired
    JWTUtil jwtUtil;

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
               customerService.updateAuthenticationType(customer, AuthenticationType.GOOGLE);}
         }

        // Generate JWT token
        String jwtToken = jwtUtil.generateToken(customerOAuth2User.getName());
        jwtToken+="Bearer";
        // Respond with JWT token
        response.addHeader("Authorization",jwtToken);
        log.info("jwtToken: {}", jwtToken);
        // Redirect to /index or respond with success message
        response.sendRedirect("/index");
          //once the user is authenticated by google, he will be led to the /index
        super.onAuthenticationSuccess(request, response, authentication);


    }


}
