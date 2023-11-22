package com.example.demo01.src.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckOutInfo {
    private float productCost;
    private float productTotal;
    private float shippingCostTotal;
    private float paymentTotal;
    private int   deliverDays;
    private Date  deliverDate;
    private boolean codSupported;


    public void setDeliverDate(int deliverDays) {
        this.deliverDays = deliverDays;
        // Calculate the new delivery date based on the current date plus deliverDays
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, deliverDays);
        this.deliverDate = calendar.getTime();
    }


    public Date getDeliverDate(){
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.DATE,deliverDays);
        return calendar.getTime();
    }

}
