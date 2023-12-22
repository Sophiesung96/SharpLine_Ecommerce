package com.example.demo01.src.Controller;

import com.example.demo01.src.Configuration.MailConfiguration;
import com.example.demo01.src.Pojo.*;
import com.example.demo01.src.Security.CustomerOAuth2User;
import com.example.demo01.src.Service.CountryService;
import com.example.demo01.src.Service.CustomerService;
import com.example.demo01.src.Service.SettingService;
import com.example.demo01.src.Service.StateService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.mail.javamail.MimeMessageHelper;

import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @Autowired
    SettingService settingService;

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    StateService stateService;

    @Autowired
    CountryService countryService;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;



    private static Logger log = LoggerFactory.getLogger(CustomerController.class);

    @GetMapping("/customerLogin")
    public String customerLoginPage(Model model) {

        return "customer-login";
    }



    @GetMapping("/register")
        public String showRegisterForm(Model model) {
            List<Country> list = customerService.listAllCountries();
            model.addAttribute("countryList", list);
            model.addAttribute("customer", new Customer());

            return "RegisterForm";
        }


        @PostMapping("/register/create_customer")
        public String createCustomer(@ModelAttribute Customer customer, RedirectAttributes ra, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
            customerService.registerCustomer(customer);
            sendVerificationEmail(customer, request);

            ra.addFlashAttribute("message", "Registration Succeed!");
            return "RegisterSuccess";
        }


        private void sendVerificationEmail(Customer customer, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
            EmailSettingBag emailSettingBag = settingService.getEmailSettings();
            //the following codes are for sending a verification email to a newly registered customer
            String customerAddress = customer.getEmail();
            String subject = emailSettingBag.getCustomerVerifySubject();
            String content = emailSettingBag.getCustomerVerifyContent();
            //create a html content
            MimeMessage mailMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage);
            helper.setFrom(emailSettingBag.getFromAddress(), emailSettingBag.getSenderName());
            helper.setTo(customerAddress);
            helper.setSubject(subject);
            content = content.replace("[[name]]", customer.getFullName());
            //give a random verification code and make it into the verify url to customer
            String verifyUrl = MailConfiguration.getSiteURL(request) + "/verify?code=" + customer.getVerificationCode();
            content = content.replace("[[URL]]", verifyUrl);
            helper.setText(content, true);
            mailSender.send(mailMessage);
            System.out.println("to Address:" + customerAddress);
            System.out.println("verify url:" + verifyUrl);

        }


        @GetMapping("/verify")
        public String verifyAccount(@RequestParam("code") String code, Model model) {
            boolean verified = customerService.verify(code);


            return verified ? "verify_success" : "verify_fail";
        }

        @GetMapping("/customers")
        public String gotoCustomerList(Model model) {
            List<Customer> list = customerService.listAllCustomer();
            List<Customer> countryList = customerService.getCountryForCustomers();
            for (Customer customer : list) {
                for (Customer customer1 : countryList) {
                    customer.setCountryName(customer1.getCountryName());
                }
            }
            model.addAttribute("list", list);
            return "CustomerList";
        }

        @GetMapping("/customers/update/Enabledstatus/{id}/{enabled}")
        public String updateEnabledStatus(@PathVariable int id, @PathVariable int enabled) {
            if (enabled == 1) {
                enabled = 0;
                customerService.updateEnabledById(enabled, id);
            } else {
                enabled = 1;
                customerService.updateEnabledById(enabled, id);

            }
            return "redirect:/customers";
        }

        @GetMapping("/customers/edit/{id}")
        public String GotoEditCustomerForm(Model model, @PathVariable int id) {
            Customer customer = customerService.EditCustomerById(id);
            List<Country> list = countryService.findAllByNameOrderByAsc();
            model.addAttribute("customer", customer);
            model.addAttribute("countryList", list);
            return "CustomerEditForm";
        }

        @GetMapping("/customers/delete/{id}")
        public String DeleteCustomer(Model model, @PathVariable int id) {
            customerService.deleteCustomerById(id);
            return "redirect:/customers";
        }

        @PostMapping("/customers/edit")
        public String EditCustomerForm(@ModelAttribute Customer customer, RedirectAttributes ra) {
            customerService.updateCustomer(customer);
            ra.addFlashAttribute("message", "You Have Succeefully Updated the Customer Info!!");
            return "redirect:/customers";
        }


        @GetMapping("/customers/detail/{id}")
        public String viewProductDetail(@PathVariable int id, Model model, HttpSession session) {
            Customer customer = customerService.findCustomerById(id);
            Customer newcustomer = customerService.findCountryPerCustomerById(id);
            model.addAttribute("customer", customer);
            model.addAttribute("newcustomer", newcustomer);


            return "customer_detail_form";
        }

    @GetMapping("/account_details")
    public String viewAccountDetails(Model model, HttpServletRequest request) {
        String customerEmail=getEmailOfAuthenticatedCustomer(request);
        Customer customer=customerService.getCustomerByEmail(customerEmail);
        List<Country>countryList=countryService.findAllByNameOrderByAsc();
        model.addAttribute("country",countryList);
        // this means that the user is not using google oauth2 but through database
        if(customer==null){
            String user[]=customerEmail.split(" ");
            Customer customer1 =customerService.findByCustomerName(user[0]);
            log.info("customer's firstname:{}",customer1.getFirstName());
            model.addAttribute("customer",customer1);
        }
        else{
            model.addAttribute("customer",customer);
        }
        return "account_form";
    }


    private String getEmailOfAuthenticatedCustomer(HttpServletRequest request){
        Object principal = request.getUserPrincipal();
        String customerEmail=null;
        String customerName=null;
        if(principal instanceof UsernamePasswordAuthenticationToken
                || principal instanceof RememberMeAuthenticationToken){
            customerName=request.getUserPrincipal().getName();
            log.info("customer name using remember me:{}",customerName);

            return customerName;
        }
        else if (principal instanceof OAuth2AuthenticationToken){
            OAuth2AuthenticationToken oAuth2AuthenticationToken= (OAuth2AuthenticationToken) principal;
            DefaultOidcUser oidUser = (DefaultOidcUser)oAuth2AuthenticationToken.getPrincipal();
            CustomerOAuth2User customerOAuth2User = new CustomerOAuth2User(oidUser);
            customerEmail=customerOAuth2User.getEmail();
            log.info("customeremail:{}",customerEmail);
            return customerEmail;
        }else{
            return customerEmail;
        }



    }

    @PostMapping("/update_account_details")
    public String updateAccountDetails(Model model,@ModelAttribute Customer customer,RedirectAttributes rs,HttpServletRequest request){
        customerService.updateCustomer(customer);
        rs.addFlashAttribute("message","Your Account Details Has been Updated!");
        String redirectOption=request.getParameter("redirect");
        String redirectURL="redirect:/account_details";
        if("addressBook".equals(redirectOption)){
            redirectURL="redirect:/addressBook";
        }else if("cart".equals(redirectOption)) {
            redirectURL="redirect:/cart";
        }else if("checkout".equals(redirectOption)) {
            redirectURL="redirect:/addressBook?redirect=checkout";
        }
        return redirectURL;
    }





}

