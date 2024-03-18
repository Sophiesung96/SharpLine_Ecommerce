package com.example.demo01.src.Controller;

import com.example.demo01.src.Configuration.MailConfiguration;
import com.example.demo01.src.Configuration.Utils.ControllerHelper;
import com.example.demo01.src.Exception.CustomerNotFoundException;
import com.example.demo01.src.Pojo.*;
import com.example.demo01.src.Service.AddressService;
import com.example.demo01.src.Service.CustomerService;
import com.example.demo01.src.Service.ShippingRateService;
import com.example.demo01.src.Service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@Slf4j
public class ShoppingCartController {
    @Autowired
    ShoppingCartService shoppingCartService;
    @Autowired
    CustomerService customerService;

    @Autowired
    ShippingRateService shippingRateService;

    @Autowired
    AddressService addressService;

    @Autowired
    ControllerHelper controllerHelper;

    @GetMapping("/cart")
    public String ViewCart(Model model, HttpServletRequest request){
        Customer customer=controllerHelper.getAuthenticatedCustomer(request);
        List<CartItem> list=shoppingCartService.listAllCartItem(customer);
        List<CartItemPName>cartItemPNameList=shoppingCartService.getJoinedProductnCustomer(customer);
        Address DefaultAddress=addressService.findefaultAddressById(customer.getId());
        ShippingRate shippingRate=null;
        boolean usePrimaryAddressAsDefault=false;
        if(DefaultAddress!=null){
             shippingRate=shippingRateService.getShippingRateForAddress(customer,DefaultAddress);
             log.info("shippgingAddress:{}",shippingRate);
        }else{
            usePrimaryAddressAsDefault=true;
            shippingRate=shippingRateService.getShippingRateforCustomer(customer);
        }
        float estimatedTotal=0.0f;
        if(list!=null||cartItemPNameList!=null){
            for(CartItem cartItem:list){
                cartItem.setList(cartItemPNameList);
            }
            for(CartItemPName detail:cartItemPNameList){
               estimatedTotal+= detail.getSubTotal();
            }
        }
        model.addAttribute("usePrimaryAddressAsDefault",usePrimaryAddressAsDefault);
        model.addAttribute("ShippingSupported",shippingRate!=null);
        model.addAttribute("cartItemlist",list);
        model.addAttribute("EstimatedTotal",estimatedTotal);
        return "Shopping_cart";
    }






}
