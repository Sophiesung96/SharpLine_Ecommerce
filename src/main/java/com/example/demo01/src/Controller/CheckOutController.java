package com.example.demo01.src.Controller;

import com.example.demo01.src.Configuration.MailConfiguration;
import com.example.demo01.src.Exception.CustomerNotFoundException;
import com.example.demo01.src.Pojo.*;
import com.example.demo01.src.Service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class CheckOutController {
    @Autowired
    CheckOutService checkOutService;

    @Autowired
    CustomerService customerService;
    @Autowired
    ShippingRateService shippingRateService;

    @Autowired
    ShoppingCartService shoppingCartService;
    @Autowired
    AddressService addressService;

    @Autowired
    OrderService orderService;

    @Autowired
    SettingService settingService;
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    CountryService countryService;

    @Autowired
    PayPalService payPalService;


    @GetMapping("/checkout")
    public String showCheckOut(Model model, HttpServletRequest request) {
        Customer customer = getAuthenticatedCustomer(request);
        List<CartItem> list = shoppingCartService.listAllCartItem(customer);
        //contains more detailed products
        List<CartItemPName> pList = shoppingCartService.getJoinedProductnCustomer(customer);
        float estimatedTotal = 0.0F;
        if (list != null || pList != null) {
            for (CartItem cartItem : list) {
                cartItem.setList(pList);

            }
            Address Defaultaddress = addressService.findefaultAddressById(customer.getId());
            ShippingRate shippingRate = new ShippingRate();
            if (Defaultaddress != null) {
                // this means that the shipping address is set to be primary address
                // so it cannot be found in address table
                // but need to be retrieved from customer table
                if (customer != null && customer.getAddress().equals(Defaultaddress.getAddress())) {
                    Customer oldCustomer = customerService.findCustomerById(customer.getId());
                    log.info("Customer's Address:{}", oldCustomer.getAddress());
                    model.addAttribute("shippingAddress", oldCustomer.getAddress());
                    shippingRate = shippingRateService.getShippingRateForAddress(oldCustomer, Defaultaddress);
                } else {
                    model.addAttribute("shippingAddress", Defaultaddress.getAddress());
                    shippingRate = shippingRateService.getShippingRateForAddress(customer, Defaultaddress);
                }
            } else {
                model.addAttribute("shippingAddress", customer.getAddress());
                shippingRate = shippingRateService.getShippingRateforCustomer(customer);

            }
            if (shippingRate == null) {
                return "redirect:/cart";

            }
            CheckOutInfo checkOutInfo = checkOutService.preparecheckOut(pList, shippingRate);
            String currencyCode=settingService.getCurrencyCode();
            log.info("deliver days:{}", checkOutInfo.getDeliverDays());
            log.info("Shipping Rate:{}",shippingRate);
            model.addAttribute("pageTitle","Check Out");
            model.addAttribute("currencyCode", currencyCode);
            model.addAttribute("checkOutInfo", checkOutInfo);
            model.addAttribute("cartItemlist", list);
            //for customers using paypal paymentgateaway
            Customer PaypalCustomer=new Customer();
             Country country=countryService.getByCountryId(customer.getCountryId());
              customer.setCountryCode(country.getCode());
            model.addAttribute("customer", customer);
            PaymentSettingBag paymentSettingBag=settingService.getPaymentSetting();
            String paypalClientID=paymentSettingBag.getClientID();
            model.addAttribute("paypalClientID",paypalClientID);

        } else {

            model.addAttribute("cartItemlist", null);


        }
        return "Checkout";
    }


    private Customer getAuthenticatedCustomer(HttpServletRequest request) {
        String email = MailConfiguration.getEmailOfAuthenticatedCustomer(request);
        if (email == null) {
            throw new CustomerNotFoundException("No Aunthenticated Customer");
        }
        if (customerService.getCustomerByEmail(email) != null) {
            return customerService.getCustomerByEmail(email);

        } else {
            String userName = email;
            Customer customer = customerService.getCustomerByfullName(userName);
            log.info(customer.getFirstName());
            return customer;

        }

    }

    @PostMapping("/placeOrder")
    public String placeOrder(HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        String paymentType = request.getParameter("paymentMethod");
        PaymentMethod paymentMethod = PaymentMethod.valueOf(paymentType);
        Customer customer = getAuthenticatedCustomer(request);
        List<CartItem> list = shoppingCartService.listAllCartItem(customer);
        List<Integer> productId = new ArrayList<>();
        //contains more detailed products
        List<CartItemPName> pList = shoppingCartService.getJoinedProductnCustomer(customer);
        float estimatedTotal = 0.0F;

        if (list != null || pList != null) {
            for (CartItem cartItem : list) {
                cartItem.setList(pList);
                productId.add(cartItem.getProduct());
            }
            Address Defaultaddress = new Address();
            Defaultaddress = addressService.findefaultAddressById(customer.getId());
            log.info("customer:{}, customer's default address is existed or nah:{}",customer.getId(), Defaultaddress != null);
            ShippingRate shippingRate = new ShippingRate();
            if (Defaultaddress != null) {
                // this means that the shipping address is set to be primary address
                // so it cannot be found in address table
                // but need to be retrieved from customer table
                if (customer != null && customer.getAddress() != null) {
                    Customer oldCustomer = customerService.findCustomerById(customer.getId());
                    log.info("Customer's Order Address:{}", oldCustomer.getAddress());
                    shippingRate = shippingRateService.getShippingRateForAddress(customer, Defaultaddress);
                } else {
                    shippingRate = shippingRateService.getShippingRateForAddress(customer, Defaultaddress);
                }
            } else {
                shippingRate = shippingRateService.getShippingRateforCustomer(customer);
            }
            CheckOutInfo checkOutInfo = checkOutService.preparecheckOut(pList, shippingRate);
            OrderDetailForm orderDetailForm=orderService.createorder(customer, Defaultaddress, list, paymentMethod, checkOutInfo);
            //clean shopping cart after placing an order
            for (int product : productId) {
                shoppingCartService.removeProduct(customer.getId(), product);
            }
            log.info("Order Total:{}",orderDetailForm.getTotal());
            sendOrderConfirmationEmail(request,orderDetailForm);

        }

            return "Order_completed";

        }

    private void sendOrderConfirmationEmail(HttpServletRequest request, OrderDetailForm order) throws MessagingException, UnsupportedEncodingException {
        EmailSettingBag emailSettingBag=settingService.getEmailSettings();
        String toAddress=order.getEmail();
        String subject=emailSettingBag.getOrderConfirmationSubject();
        subject=subject.replace("[orderId]",String.valueOf(order.getId()));
        String content=emailSettingBag.gettOrderConfirmationContent();
        MimeMessage mimeMessage=javaMailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(mimeMessage);
        helper.setFrom(emailSettingBag.getFromAddress(),emailSettingBag.getSenderName());
        helper.setTo(toAddress);
        helper.setSubject(subject);
        CurrencySettingBag currencySettingBag=settingService.getCurrencySetting();
        String totalAmount=MailConfiguration.formatCurrency(order.getTotal(),currencySettingBag);
        content=content.replace("[[name]]",order.getCustomerFullName());
        content=content.replace("[[orderId]]",String.valueOf(order.getId()));
        content=content.replace("[orderTime]",order.getOrderTime());
        content=content.replace("[[shippingAddress]]",order.getShippingAddress());
        content=content.replace("[[total]]",totalAmount);
        content=content.replace("[[paymentMethod]]",order.getPaymentMethod());
        helper.setText(content,true);
        javaMailSender.send(mimeMessage);

    }

    @PostMapping("/process_paypal_order")
    public String processPayPalOrder(HttpServletRequest request,Model model){
        String orderId=request.getParameter("orderId");
        String pageTitle=null;
        String message=null;
            try {
                if(payPalService.ValidateApi(orderId)){
                return placeOrder(request);
                }else{
                    pageTitle="Check Out Failure";
                    message="ERROR: Transaction could not be completed because order information" +
                            "is invalid";
                }
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
               message="ERROR:Transaction failed due to error: "+ e.getMessage();
            }
             model.addAttribute("pageTitle",pageTitle);
             model.addAttribute("message",message);

        return message;
    }


}
