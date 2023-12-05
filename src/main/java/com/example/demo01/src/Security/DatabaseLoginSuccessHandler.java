package com.example.demo01.src.Security;

import com.example.demo01.src.Configuration.JWTUtil;
import com.example.demo01.src.Pojo.AuthenticationType;
import com.example.demo01.src.Pojo.Customer;
import com.example.demo01.src.Service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class DatabaseLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private CustomerService customerService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        CustomUserDetail customUserDetail = new CustomUserDetail(
                userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );
        String name = customUserDetail.getUsername();
        String fullName[] = name.split(" ");
        Customer customer = customerService.findByCustomerName(fullName[0]);
        log.info("name:{}", name);
        customerService.updateAuthenticationType(customer, AuthenticationType.DATABASE);

        // Generate JWT token
        String jwtToken = jwtUtil.generateToken(customUserDetail.getUsername());
        jwtToken="Bearer "+jwtToken;

       response.setHeader("Authorization",jwtToken);
        log.info("jwtToken: {}", jwtToken);
        // Redirect to /index or respond with success message
       response.sendRedirect("/index");

        super.onAuthenticationSuccess(request, response, authentication);
    }


}
