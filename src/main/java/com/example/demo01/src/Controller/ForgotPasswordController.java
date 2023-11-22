package com.example.demo01.src.Controller;

import com.example.demo01.src.main.Configuration.MailConfiguration;
import com.example.springboot_ecommerce.Exception.CustomerNotFoundException;
import com.example.springboot_ecommerce.Pojo.BrandCategoryName;
import com.example.springboot_ecommerce.Pojo.Customer;
import com.example.springboot_ecommerce.Pojo.EmailSettingBag;
import com.example.springboot_ecommerce.Service.CustomerService;
import com.example.springboot_ecommerce.Service.SettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
@Slf4j
public class ForgotPasswordController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private SettingService settingService;

    @Autowired
    JavaMailSender mailSender;





    @GetMapping("/forgot_password")
    public String showRestPasswordPage(){
        return "forgot_password_form";
    }
    @PostMapping("/forgot_password")
    public String processResetPassword(HttpServletRequest request, Model model){
       String email=request.getParameter("email");
       try{
         String token= customerService.updateResetPasswordToken(email);
         String link= MailConfiguration.getSiteURL(request)+"/reset_password?token="+token;
         sendEmail(link,email);
         log.info("email:{}",email);
         log.info("token:{}",token);
           model.addAttribute("message","We have send a reset password link to your email."
           +"Please check");
       }catch(CustomerNotFoundException e){
           model.addAttribute("error",e.getMessage());
        } catch (MessagingException | UnsupportedEncodingException e) {
           model.addAttribute("error","Could not send Email");
       } ;

        return "forgot_password_form";
    }

        private void sendEmail(String link,String email) throws MessagingException, UnsupportedEncodingException {
            EmailSettingBag emailSettingBag = settingService.getEmailSettings();
            String toAddress=email;
            String subject="Here's the Link To Reset Your Password";
            String content="<p>Hello,</p>"
                    +"<p>You have requested to reset your password.</p>"
                    +"<p>Click the link below to change your password:</p>"
                    +"<p><a href=\""+link+"\">Change My Password</a></p>"
                    +"<br>"
                    +"<p>Ignore this email if you do remember your password,"
                    +"or you may have not made the request.</p>";
            MimeMessage mimeMessage=mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setFrom(emailSettingBag.getFromAddress(), emailSettingBag.getSenderName());
            helper.setTo(toAddress);
            helper.setSubject(subject);
            helper.setText(content,true);
           mailSender.send(mimeMessage);
        }

        @GetMapping("/reset_password")
        public String showresetForm(@RequestParam("token") String token,Model model){
            Customer customer=customerService.GetByResetPasswordToken(token);
            if(customer!=null){
                model.addAttribute("customer",customer);
                model.addAttribute("token",token);
            }else{
                model.addAttribute("message","Invalid Token");
                return "Message";

            }
        return "reset_password_form";

        }
        @PostMapping("/reset_password")
        public String processResetForm(HttpServletRequest request,Model model){
        try{
            String token=request.getParameter("token");
            String password=request.getParameter("password");
            customerService.updateCustomerNewPasswordByToken(token,password);
            model.addAttribute("title","Reset Password");
            model.addAttribute("message","You Have Successfully changed Your Password!");
            return "Message";
        }catch(CustomerNotFoundException e){
            model.addAttribute("message",e.getMessage());
            return "Message";
        }


        }
}
