package com.example.demo01.src.Controller;

import com.example.demo01.src.Configuration.MailConfiguration;
import com.example.demo01.src.Exception.CustomerNotFoundException;
import com.example.demo01.src.Exception.OrderNotFoundExcption;
import com.example.demo01.src.Pojo.*;
import com.example.demo01.src.Security.CustomUserDetail;
import com.example.demo01.src.Service.CustomerService;
import com.example.demo01.src.Service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Slf4j
public class OrderRestController {
    @Autowired
    private OrderService orderService;
    @Autowired
    CustomerService customerService;


    @PostMapping("/orders_shipper/update/{id}/{status}")
    public Response updateStatus(@PathVariable int id,@PathVariable String status){
        orderService.updateStatus(id,status);
        return new Response(id,status);
    }


    static class Response{
        private Integer orderId;
        private String status;

        public Response(Integer orderId, String status) {
            this.orderId = orderId;
            this.status = status;
        }

        public Integer getOrderId() {
            return orderId;
        }

        public void setOrderId(Integer orderId) {
            this.orderId = orderId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }


    private Customer getAuthenticatedCustomer(HttpServletRequest request){
        String email = MailConfiguration.getEmailOfAuthenticatedCustomer(request);
        if (email == null) {
            throw new CustomerNotFoundException("No Aunthenticated Customer");
        }
        if (customerService.getCustomerByEmail(email) != null) {
            return customerService.getCustomerByEmail(email);
        } else {
            String userName = email;
            log.info("Customer's FullName:{}",userName);
            Customer customer = customerService.getCustomerByfullName(userName);
            return customer;
        }
    }




    @PostMapping("/customers/Order/return")
    public ResponseEntity<?> handleOrderReturnRequest(@RequestBody OrderReturnRequest returnRequest,HttpServletRequest request){
        Customer customer=new Customer();
        Order order=new Order();
        log.info("OrderId:{}",returnRequest.getId());
        log.info("Reason:{}",returnRequest.getReason());
        log.info("Note:{}",returnRequest.getNote());
        try{
            customer=getAuthenticatedCustomer(request);
            log.info("Customer's id:{}",customer.getId());
        }catch(CustomerNotFoundException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Authentication Required");
        }

        try{
               order=orderService.getOrderDetailByIdAndCustomer(customer.getId(), returnRequest.getId() );
                List<TableOrderDetail> orderStatusList=orderService.getCustomerTrackStatusList( customer.getId(), returnRequest.getId());
                // check whether the orderStatusList is null
                if(orderStatusList!=null){
                    order.setOrderTrackList(orderStatusList);
                    orderService.setOrderReturnRequested(returnRequest,customer,order);
                }


        }catch(OrderNotFoundExcption exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(new OrderReturnResponse(returnRequest.getId()));
    }

    //for testing OrderRestController only
    private Customer TestgetAuthenticatedCustomer() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        // Access user details from the authentication object
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetail) {
            CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();
            String userName= userDetails.getUsername();
            Customer customer = customerService.findByCustomerName(userName);
            log.info("Authenticated Customer for accessing shopping cart ",customer.getFirstName());
            return customer;
        }

        // If authentication is not available or UserDetails is not present, return null or handle accordingly
        return null;
    }

}
