package com.example.demo01.src.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class PayPalOrderResponse {

    private String id;
    private String status;

    public boolean Validate(String orderId){
        return id.equals(orderId) && status.equals("COMPLETED");
    }

}
