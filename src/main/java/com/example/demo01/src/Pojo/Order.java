package com.example.demo01.src.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private int id;
    private int customerId;
    private String orderTime;
    private String PaymentMethod;
    private float ProductCost;
    private float  ShippingCost;
    private float Total;
    private float Tax;
    private float SubTotal;
    private String Status;
    private String FirstName;
    private String LastName;
    private String PhoneNumber;
    private String Addressline1;
    private String Addressline2;
    private String City;
    private String State;
    private String Country;
    private String PostalCode;
    private int DeliverDays;
    private Date DeliverDate;




}
