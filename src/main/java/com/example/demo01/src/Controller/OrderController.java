package com.example.demo01.src.Controller;

import com.example.demo01.src.Pojo.Order;
import com.example.demo01.src.Pojo.OrderDetailForm;
import com.example.demo01.src.Pojo.OrderDetails;
import com.example.demo01.src.Pojo.PageNumber;
import com.example.demo01.src.Service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@Slf4j
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/orders/{pageno}")
    public String getPage(@PathVariable int pageno, Model model){
        List<Order> list=orderService.findAll(pageno);
        int total=orderService.getTotalPage();
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
        OrderDetailForm order=orderService.getOrderById(id);
        model.addAttribute("order",order);
        return "OrderDetails";
    }

    @GetMapping("/orders/edit/{id}")
    public String showOrderEdit(@PathVariable int id){

        return "OrderEditForm";
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
