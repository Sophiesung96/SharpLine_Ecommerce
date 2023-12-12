package com.example.demo01.src.Controller;

import com.example.demo01.src.Exception.OrderNotFoundExcption;
import com.example.demo01.src.Pojo.*;
import com.example.demo01.src.Service.OrderService;
import com.example.demo01.src.Service.OrderTrackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderTrackService orderTrackService;




    @GetMapping("/orders/{pageno}")
    public String getPage(@PathVariable int pageno, Model model){
        List<Order> list=orderService.findAll(pageno);
        List<Integer> total=orderService.getTotalPage();
        int isCOD=0;
        for(Order order:list){
            if(order.getPaymentMethod().equals("COD")){
               isCOD=1;
                model.addAttribute("isCOD",isCOD);
            }else{
                isCOD=0;
                model.addAttribute("isCOD",isCOD);
            }

        }
        model.addAttribute("list",list);
        model.addAttribute("currentPage",pageno);
        model.addAttribute("total",total);
        return "OrderList";
    }
    @PostMapping("/orders/{pageno}/keyword")
    public String getPageByKeyword(@PathVariable int pageno, HttpServletRequest request,Model model){
        String keyword=request.getParameter("keyword");
        log.info("keyword",keyword);
       List<Order>list= orderService.findAllByKeyword(pageno,keyword);
       model.addAttribute("list",list);
        model.addAttribute("currentPage",pageno);

        return "OrderList";
    }



    @GetMapping("/orders/detail/{id}")
    public String getOrderDetails(@PathVariable int id,Model model){
        OrderDetailForm order=orderService.getOrderDetailById(id);
        List<OrderTrack> list=orderTrackService.findOrderTrackById(order.getId());
        model.addAttribute("order",order);
        model.addAttribute("tracklist",list);
        return "OrderDetails";
    }

    @GetMapping("/orders/edit/{id}")
    public String showOrderEdit(@PathVariable int id,Model model,RedirectAttributes rs){
        try{
            Order order=orderService.getOrderById(id);
            List<Country>list=orderService.listAllCountries();
            List<PaymentMethod> paymentMethodList = getPaymentMethods();
            List<OrderStatus>orderStatusList=getStatus();
            List<OrderDetailForm> orderDetailFormList=orderService.getOrderDetailsList(order.getId());
            model.addAttribute("pageTitle","Edit Order (ID:"+id+")");
            model.addAttribute("order",order);
            model.addAttribute("orderDetailFormList",orderDetailFormList);
            model.addAttribute("orderStatusList",orderStatusList);
            model.addAttribute("paymentMethodList",paymentMethodList);
            model.addAttribute("countries",list);
            return "OrderEditForm";
        }
        catch(OrderNotFoundExcption ex){
            rs.addFlashAttribute("message",ex.getMessage());
            return "redircet:/orders/1";
        }


    }


    private List<PaymentMethod> getPaymentMethods() {
        List<PaymentMethod>paymentMethodList=new ArrayList<>();
        paymentMethodList.add(PaymentMethod.COD);
        paymentMethodList.add(PaymentMethod.CREDIT_CARD);
        paymentMethodList.add(PaymentMethod.PAYPAL);
        return paymentMethodList;
    }

    private  List<OrderStatus> getStatus() {
        List<OrderStatus>orderStatusList=new ArrayList<>();
        orderStatusList.add(OrderStatus.NEW);
        orderStatusList.add(OrderStatus.PAID);
        orderStatusList.add(OrderStatus.CANCELED);
        orderStatusList.add(OrderStatus.PICKED);
        orderStatusList.add(OrderStatus.PACKAGED);
        orderStatusList.add(OrderStatus.DELIVERED);
        orderStatusList.add(OrderStatus.SHIPPING);
        orderStatusList.add(OrderStatus.PROCESSING);
        orderStatusList.add(OrderStatus.DELIVERED);
        orderStatusList.add(OrderStatus.REFUNDED);
        orderStatusList.add(OrderStatus.REFUNDED);
        return orderStatusList;
    }

    @PostMapping("/update/order")
    public String EditOrder(@ModelAttribute Order order,RedirectAttributes rs){

        return "redirect:/orders/1";
    }

    @GetMapping("/orders/delete/{id}")
    public String DeleteOrder(@PathVariable int id){
           orderService.DeleteOrderById(id);
        return "redirect:/orders/1";
    }
    @GetMapping("/updatePaymentMethod/{isCOD}/{id}")
    public String updatePaymentMethod(@PathVariable int isCOD, @PathVariable int id, RedirectAttributes rs){
        log.info("isCOD:{},id:{}",isCOD,id);
        String COD=null;
        if(isCOD==1){
            COD="COD";
            orderService.updatePaymentMethod(COD,id);
            rs.addFlashAttribute("message","COD support for shipping rate ID"+" "+id +"has been updated!");
            return "redirect:/orders/1";
        }else{
            COD="CREDIT_CARD";
            orderService.updatePaymentMethod(COD,id);
            rs.addFlashAttribute("message","COD support for shipping rate ID"+" "+id +"has been updated!");
            return "redirect:/orders/1";
        }


    }


}
