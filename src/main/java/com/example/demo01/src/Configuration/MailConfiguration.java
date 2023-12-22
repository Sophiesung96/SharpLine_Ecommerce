package com.example.demo01.src.Configuration;

import com.example.demo01.src.Pojo.CurrencySettingBag;
import com.example.demo01.src.Security.CustomerOAuth2User;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Properties;
@Configuration
@Log
public class MailConfiguration {


    public static String getSiteURL(HttpServletRequest request) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();

        // Build the complete URL with the appropriate scheme, server name, port, and context path
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(scheme).append("://").append(serverName);

        // Append the port number if it's not the default port (80 for HTTP or 443 for HTTPS)
        if (("http".equals(scheme) && serverPort != 80) || ("https".equals(scheme) && serverPort != 443)) {
            urlBuilder.append(":").append(serverPort);
        }

        // Append the context path
        urlBuilder.append(contextPath);
        return urlBuilder.toString();
    }

    public static String getEmailOfAuthenticatedCustomer(HttpServletRequest request) {
        Object principal = request.getUserPrincipal();
        String customerEmail = null;
        if (principal == null) return null;
        if (principal instanceof UsernamePasswordAuthenticationToken ||
                principal instanceof RememberMeAuthenticationToken) {
            customerEmail = request.getUserPrincipal().getName();
        } else if (principal instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oAuth2AuthenticationToken = ((OAuth2AuthenticationToken) principal);
            DefaultOidcUser defaultOidcUser = (DefaultOidcUser) oAuth2AuthenticationToken.getPrincipal();
            CustomerOAuth2User customerOAuth2User = new CustomerOAuth2User(defaultOidcUser);
            customerEmail = customerOAuth2User.getEmail();
            return customerEmail;
        }
        return customerEmail;
    }


    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("sharplineservicee@gmail.com");
        mailSender.setPassword("qlzqodkbtguofgqf");
        mailSender.setDefaultEncoding("utf-8");
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.protocol", "smtp");
        props.put("mail.debug", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return mailSender;
    }

    public  static String formatCurrency(float amount, CurrencySettingBag currencySettingBag){
        String symbol=currencySettingBag.getSymbol();
        String symbolPosition=currencySettingBag.getSymbolPosition();
        String decimalPointType=currencySettingBag.getDecimalPointType();
        String ThousandPointType=currencySettingBag.getThousandsPointType();
        int DecimalDigit=currencySettingBag.getDecimalDigits();
        String pattern=symbolPosition.equals("Before_Price")?symbol:"";
        pattern+="###,###";
        if(DecimalDigit>0){
            pattern+=".";
            for(int i=0;i<=DecimalDigit;i++){
                pattern+="#";
            }
        }
        pattern+=symbolPosition.equals("After_Price")?symbol:"";
        char thousandSeparator=ThousandPointType.equals("POINT")? '.':',';
        char decimalSeparator=decimalPointType.equals("POINT")? '.':',';
        DecimalFormatSymbols decimalFormatSymbols=DecimalFormatSymbols.getInstance();
        decimalFormatSymbols.setDecimalSeparator(decimalSeparator);
        decimalFormatSymbols.setGroupingSeparator(thousandSeparator);
        DecimalFormat formatter=new DecimalFormat(pattern,decimalFormatSymbols);
        return  formatter.format(amount);
    }





}
