package com.example.demo01.src.Controller;

import com.example.demo01.src.main.Configuration.MailConfiguration;
import com.example.springboot_ecommerce.Exception.CustomerNotFoundException;
import com.example.springboot_ecommerce.Pojo.*;
import com.example.springboot_ecommerce.Service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
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
            log.info("deliver days:{}", checkOutInfo.getDeliverDays());
            log.info("Shipping Rate:{}",shippingRate);
            model.addAttribute("checkOutInfo", checkOutInfo);
            model.addAttribute("cartItemlist", list);
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
    public String placeOrder(HttpServletRequest request) {
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
            log.info("default address is existed or nah:{}", Defaultaddress != null);
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
            orderService.createorder(customer, Defaultaddress, list, paymentMethod, checkOutInfo);
            //clean shopping cart after placing an order
            for (int product : productId) {
                shoppingCartService.deleteByCustomer(customer, product);
            }
        }

            return "Order_completed";

        }






}
