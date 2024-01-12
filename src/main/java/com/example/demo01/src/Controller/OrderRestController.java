package com.example.demo01.src.Controller;

import com.example.demo01.src.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderRestController {
    @Autowired
    private OrderService orderService;


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
}
