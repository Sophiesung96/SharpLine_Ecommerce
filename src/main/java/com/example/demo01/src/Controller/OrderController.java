package com.example.demo01.src.Controller;

import com.example.demo01.src.Exception.OrderNotFoundExcption;
import com.example.demo01.src.Pojo.*;
import com.example.demo01.src.Service.OrderService;
import com.example.demo01.src.Service.OrderTrackService;
import com.example.demo01.src.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderTrackService orderTrackService;

   @Autowired
    UserService userService;


    @GetMapping("/orders/{pageno}")
    public String getPage(@PathVariable int pageno, Model model,  @AuthenticationPrincipal Authentication user,HttpServletRequest request){
        List<Order> list=orderService.findAll(pageno);
        for(Order order:list){

            log.info("order id:{}",order.getId());
          //Get the order Status of each order
            List<TableOrderDetail> orderStatusList=orderService.getTrackStatusList(order.getCustomerId());
            // check whether the orderStatusList is null
            if(orderStatusList!=null){
                orderStatusList.stream().forEach(detail->log.info("order Status:{}",detail.getStatusCondition()));
                order.setOrderTrackList(orderStatusList);
            }
        }
        List<Integer> total=orderService.getTotalPage();
        int isCOD=0;
        //get the authenticated user's name
        //and check his authority
        List<UsersRole> Rolelist = getUsersRoles(user);
        for(UsersRole role:Rolelist){
           if(role.getName().equals("Admin") ||role.getName().equals("Shipper")){
               log.info("Authenticated User Role:{}",role.getName());
              model.addAttribute("list",list);
               model.addAttribute("currentPage",pageno);
             model.addAttribute("total",total);
               //Retrieve the updated orderList based on the keyword
               if (model.getAttribute("orderList")!=null && model.getAttribute("Page")!= null){
                   List<Order> orderList = (List<Order>) model.getAttribute("orderList");
                   Integer currentPage = (Integer) model.getAttribute("Page");
                   log.info("updated list:{}",model.getAttribute("orderList"));
                   log.info("updated currentpage:{}",model.getAttribute("Page"));
                   model.addAttribute("list", orderList);
                   model.addAttribute("currentPage",currentPage);
                   model.addAttribute("total",total);
                   return "OrderShipper";
               }
               return "OrderShipper";
           }
        }

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
   //Get authenticated user's role
    private List<UsersRole> getUsersRoles(Authentication user) {
        Users Authenticateduser=userService.getUserIdByName(user.getName());
        List<UsersRole> Rolelist=userService.FindUserRoleByUser(Authenticateduser);
        return Rolelist;
    }


    @PostMapping("/orders/{pageno}/keyword")
    public String getPageByKeyword(@PathVariable int pageno, HttpServletRequest request, RedirectAttributes re) {
        String keyword = request.getParameter("keyword");
        log.info("Keyword: {}", keyword);

        List<Order> list = orderService.getOrderTrackByKeyword(keyword, pageno);
        re.addFlashAttribute("orderList", list);
        re.addFlashAttribute("Page", pageno);


        return "redirect:/orders/1";
    }




    @GetMapping("/orders/detail/{id}")
    public String getOrderDetails(@PathVariable int id,Model model,@AuthenticationPrincipal  Authentication user ){
        OrderDetailForm order=orderService.getOrderDetailById(id);
        List<OrderTrack> list=orderTrackService.findOrderTrackById(order.getId());
        List<TableOrderDetail> orderDetailFormList=orderService.getOrderDetailsList(order.getId());
        boolean isvisibleforAdminorSalesPerson=true;
        List<UsersRole> Rolelist=getUsersRoles(user);
        for(UsersRole role:Rolelist){
            if(role.getName().equals("Admin") ||role.getName().equals("Shipper")){
                log.info("logged user's role name:{}",role.getName());
                isvisibleforAdminorSalesPerson=false;
            }
        }
        model.addAttribute("isvisibleforAdminorSalesPerson",isvisibleforAdminorSalesPerson);
        model.addAttribute("orderDetailFormList",orderDetailFormList);
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
            List<TableOrderDetail> orderDetailFormList=orderService.getOrderDetailsList(order.getId());
            List<OrderTrack> Tracklist=orderTrackService.findOrderTrackById(order.getId());
            model.addAttribute("pageTitle","Edit Order (ID:"+id+")");
            model.addAttribute("order",order);
            model.addAttribute("orderDetailFormList",orderDetailFormList);
            model.addAttribute("orderStatusList",orderStatusList);
            model.addAttribute("paymentMethodList",paymentMethodList);
            model.addAttribute("countries",list);
            model.addAttribute("tracklist",Tracklist);
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
        return orderStatusList;
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

    @PostMapping("/order/update")
    public String SaveEditedOrderDetail(@ModelAttribute Order order,HttpServletRequest request,RedirectAttributes rs){
        String countryName=request.getParameter("countryName");
        String deliverDate=request.getParameter("deliverdate");
        String customerId=request.getParameter("customerId");
        String orderTime=request.getParameter("orderTime");
        int orderId=Integer.parseInt(request.getParameter("orderId"));
        order.setCountry(countryName);
        order.setId(orderId);
        order.setOrderTime(orderTime);
        order.setCustomerId(Integer.parseInt(customerId));
        order.setDeliverDateonForm(deliverDate);
        //update or create a new orderDetail
        updateProductDetails(order,request);
        //update or create a new orderTrack
       updateOrderTracks(order,request);
        orderService.updateOriginalOrderById(order);
        rs.addFlashAttribute("message","The order ID"+" "+order.getId()+" has been updated successfully");
        return "redirect:/orders/1";
    }

    private void updateOrderTracks(Order order, HttpServletRequest request) {
        String[] trackId = request.getParameterValues("trackId");
        log.info("trackId length:{}", trackId.length);
        String[] trackStatuses = request.getParameterValues("trackStatus");
        String[] trackNotes = request.getParameterValues("trackNotes");
        String[] trackDates = request.getParameterValues("trackDate");
        List<OrderTrack> orderTrackList = orderTrackService.findOrderTrackById(order.getId());
        OrderTrack orderTrack = new OrderTrack();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        for (int i = 0; i < trackId.length; i++) {
            log.info("trackId: {}", trackId[i]);
            log.info("trackStatuses: {}", trackStatuses[i]);
            log.info("trackNotes: {}", trackNotes[i]);
            log.info("trackDates: {}", trackDates[i]);

            int Id = Integer.parseInt(trackId[i]);
            orderTrack.setOrderId(order.getId());

            try {
                orderTrack.setUpdatedTime(dateFormat.parse(trackDates[i]));
            } catch (ParseException e) {
                e.printStackTrace();
                // Handle the parse exception as needed
            }
            orderTrack.setStatus(trackStatuses[i]);
            orderTrack.setNotes(trackNotes[i]);

            if (Id== 0) {
                // It's a new track, create it
                orderTrackService.createOrderTrack(orderTrack);
                log.info("TrackID: {}", Id);
                log.info("Latest trackStatus: {}", trackStatuses[i]);
                orderService.updateTrackStatus(trackStatuses[i], order);
            } else {
                // Update the existing track if status is not "NEW"
                // and the order's id is not 1
                if (!trackStatuses[i].equals("NEW")&&order.getId()!=1) {
                    orderTrack.setId(Id);
                    orderTrackService.updateOrderTrackByOrderId(orderTrack, order.getId());
                }
            }
        }



    }


    private void updateProductDetails(Order order, HttpServletRequest request) {
        String [] detailsIds=request.getParameterValues("id");
        String [] productIds=request.getParameterValues("productId");
        String [] productCosts=request.getParameterValues("productCost");
        String [] productShipCosts=request.getParameterValues("productShipCost");
        String [] productSubtotals=request.getParameterValues("productSubtotal");
        String [] productQuantities=request.getParameterValues("quantity");
        String [] productPrices=request.getParameterValues("productPrice");
        List<OrderDetails> orderDetailsList=orderService.getOrderDetailsByOrderId(order.getId());
        for(int i=0;i<productIds.length;i++){
            log.info("detailsId:{}",detailsIds[i]);
            log.info("productIds:{}",productIds[i]);
            log.info("productCosts:{}",productCosts[i]);
            log.info("productShipCosts:{}",productShipCosts[i]);
            log.info("productSubtotals:{}",productSubtotals[i]);
            log.info("Quantity:{}",productQuantities[i]);
            log.info("productPrices:{}",productPrices[i]);
            OrderDetails orderDetails=new OrderDetails();
            Integer detailsId=Integer.parseInt(detailsIds[i]);
            Integer productId=Integer.parseInt(productIds[i]);
            Integer quantity=Integer.parseInt(productQuantities[i]);
            orderDetails.setProductId(productId);
            orderDetails.setQuantity(quantity);
            orderDetails.setProductCost(Float.parseFloat(productCosts[i]));
            orderDetails.setShippingCost(Float.parseFloat(productShipCosts[i]));
            orderDetails.setSubTotal(Float.parseFloat(productSubtotals[i]));
            orderDetails.setUnitPrice(Float.parseFloat(productPrices[i]));
           // update the existed OrderDetail
            if(orderDetailsList.get(i).getId()==detailsId&&detailsId>0){
                orderDetails.setId(order.getId());
                orderService.updateOrderDetailsByOrderId(orderDetails);
                //a new OrderDetail is being added to the list
            }else if (detailsId==0){
                Customer customer=new Customer(order.getCustomerId());
                orderService.createOrderDetail(order,orderDetailsList,customer);


            }
        }
    }

}
