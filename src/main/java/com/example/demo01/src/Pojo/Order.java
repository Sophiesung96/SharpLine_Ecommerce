package com.example.demo01.src.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
    private List<OrderDetails> orderDetailList;
    private List<TableOrderDetail> orderTrackList;
    private List<String> productNameList;

    public Order(int id) {
        this.id = id;
    }


    public String getCustomerFullName(){
        return this.FirstName+" "+this.LastName;
    }

    public String getAddress() {
        StringBuilder addressBuilder = new StringBuilder();
        addressBuilder.append(this.FirstName);

        if (this.LastName != null && !this.LastName.isEmpty()) {
            addressBuilder.append(" ").append(this.LastName);
        }
        if (this.Addressline1 != null &&!this.Addressline1.isEmpty()) {
            addressBuilder.append(", ").append(this.Addressline1);
        }
        if (this.Addressline2 != null && !this.Addressline2.isEmpty()) {
            addressBuilder.append(" ").append(this.Addressline2);
        }
        if (this.City!= null && !this.City.isEmpty()) {
            addressBuilder.append(", ").append(this.City);
        }
        if (this.State!=null && !this.State.isEmpty()) {
            addressBuilder.append(", ").append(this.State);
        }
        if (this.PostalCode !=null &&!this.PostalCode.isEmpty()) {
            addressBuilder.append(". Postal Code: ").append(this.PostalCode);
        }
        if (this.PhoneNumber!=null &&!this.PhoneNumber.isEmpty()) {
            addressBuilder.append(". Phone Number: ").append(this.PhoneNumber);
        }
        return addressBuilder.toString();
    }

    public String getDeliverDateonForm(){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String date=simpleDateFormat.format(this.DeliverDate);
        return date;
    }

    public void setDeliverDateonForm(String date){
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date deliverDate = simpleDateFormat.parse(date);
            this.DeliverDate = deliverDate;
        } catch(ParseException e) {
            e.printStackTrace();
        }
    }
    public String getRecipientAddress() {
        String address=Addressline1;
        StringBuilder addressBuilder = new StringBuilder();
        addressBuilder.append(address);


        if (this.Addressline2 != null && !this.Addressline2.isEmpty()) {
            addressBuilder.append(" ").append(this.Addressline2);
        }
        if (this.City!= null && !this.City.isEmpty()) {
            addressBuilder.append(", ").append(this.City);
        }
        if (this.State!=null && !this.State.isEmpty()) {
            addressBuilder.append(", ").append(this.State);
        }
        if (this.PostalCode !=null &&!this.PostalCode.isEmpty()) {
            addressBuilder.append(".").append(this.PostalCode);
        }
        return addressBuilder.toString();
    }

    public boolean isCOD(){
        return this.PaymentMethod.equals("COD");
    }

    public boolean hasStatus(String status) {
        List<TableOrderDetail>list=new ArrayList<>();
         list=this.orderTrackList;
        for (TableOrderDetail detail : list) {
            if (status != null && !status.isEmpty()) {
                if (status.equals(detail.getStatusCondition())) {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean isReturnRequested(){return true;};
    public boolean isPicked(){
        return hasStatus("PICKED");
    }
    public boolean isDelivered(){
        return hasStatus("DELIVERED");
    }
    public boolean isShipping(){
        return hasStatus("SHIPPING");
    }
    public boolean isReturned(){
        return hasStatus("RETURNED");
    }
    public boolean isPackaged(){
        return hasStatus("PACKAGED");
    }




}

