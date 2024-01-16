package com.example.demo01.src.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableOrderDetail {
    private int id;
    private int customerId;
    private int productId;
    private int TotalPage;
    private int enabled;
    private String email;
    private String orderTime;
    private String Productname;
    private String ProductmainImage;
    private String paymentMethod;
    private String Customeremail;
    private float unitPrice;
    private float productCost;
    private float shippingCost;
    private float DetailproductCost;
    private float subTotal;
    private float tax;
    private float total;
    private String status;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String addressline1;
    private String addressline2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private int deliverDays;
    private int quantity;
    private String deliverDate;
    private String paymentmethod;
    private String StatusCondition;

    public String getCustomerFullName(){
        if(this.lastName!=null && this.lastName.length()>2){
            return this.firstName+" "+this.lastName;
        }
        return this.firstName;
    }


    public String getShippingAddress() {
        StringBuilder addressBuilder = new StringBuilder();
        addressBuilder.append(this.firstName);

        if (this.lastName != null && !this.lastName.isEmpty()) {
            addressBuilder.append(" ").append(this.lastName);
        }
        if (this.addressline1 != null &&!this.addressline1.isEmpty()) {
            addressBuilder.append(", ").append(this.addressline1);
        }
        if (this.addressline2 != null && !this.addressline2.isEmpty()) {
            addressBuilder.append(" ").append(this.addressline2);
        }
        if (this.city!= null && !this.city.isEmpty()) {
            addressBuilder.append(", ").append(this.city);
        }
        if (this.state!=null && !this.state.isEmpty()) {
            addressBuilder.append(", ").append(this.state);
        }
        if (this.postalCode !=null &&!this.postalCode.isEmpty()) {
            addressBuilder.append(". Postal Code: ").append(this.postalCode);
        }
        if (this.phoneNumber!=null &&!this.phoneNumber.isEmpty()) {
            addressBuilder.append(". Phone Number: ").append(this.phoneNumber);
        }
        return addressBuilder.toString();
    }



    public Date getDeliverDate(){
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.DATE,deliverDays);
        return calendar.getTime();
    }


    public String getMainImagePath(){
        if( this.ProductmainImage==null){
            return "/image/default.jpeg";
        }
        else{

            return"/product-images/"+this.productId+"/"+this.ProductmainImage;
        }
    }





}
