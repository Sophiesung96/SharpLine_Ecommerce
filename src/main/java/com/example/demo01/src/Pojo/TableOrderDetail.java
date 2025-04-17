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
    private String CategoryName;
    private String ProductIds;
    private String DetailproductCosts;
    private String SubTotals;
    private String Productnames;
    private String ProductMainImages;
    private String ProductCosts;
    private String ShippingCosts;
    private String Quantities;
    private String UnitPrices;
    private String CategoryNames;
    private List<String> SortedCategoryNames;
    private List<String> SortedDetailproductCosts;
    private List<String> SortedProductId;
    private List<String> SortedProductName;
    private List<String> SortedQuantity;
    private List<String> SortedSubtotal;
    private List<String> SortedUnitprice;
    private List<String> SortedMainImage;
    private List<String> SortedproductCosts;
    private List<String> SortedShippingCosts;



    public TableOrderDetail(float productCost, float shippingCost, float subTotal, int quantity, String categoryName) {
        this.productCost = productCost;
        this.shippingCost = shippingCost;
        this.subTotal = subTotal;
        this.quantity = quantity;
        CategoryName = categoryName;
    }

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

    public List<String> getSortedProductName() {

        SortedProductName=new ArrayList<>();
        if(Productnames!=null)
        {
            String [] splitProductName=Productnames.split(",");
            if(splitProductName.length>0){
                for(String s:splitProductName){
                    SortedProductName.add(s);
                }
            }
        }
        return SortedProductName;
    }

    public List<String> getSortedQuantity() {

        SortedQuantity=new ArrayList<>();
        if(Quantities!=null)
        {
            String [] splitQuantity=Quantities.split(",");
            if(splitQuantity.length>0){
                for(String s:splitQuantity){
                    SortedQuantity.add(s);
                }
            }
        }
        return SortedQuantity;
    }

    public List<String> getSortedSubtotal() {

        SortedSubtotal=new ArrayList<>();
        if(SubTotals!=null)
        {
            String [] splitSubtotal=SubTotals.split(",");
            if(splitSubtotal.length>0){
                for(String s:splitSubtotal){
                    SortedSubtotal.add(s);
                }
            }
        }
        return SortedSubtotal;
    }

    public List<String> getSortedUnitprice() {

        SortedUnitprice=new ArrayList<>();
        if(UnitPrices!=null)
        {
            String [] splitUnitPrices=UnitPrices.split(",");
            if(splitUnitPrices.length>0){
                for(String s:splitUnitPrices){
                    SortedUnitprice.add(s);
                }
            }
        }
        return SortedUnitprice;
    }

    public List<String> getSortedMainImage() {
        SortedMainImage = new ArrayList<>();
        String baseUrl = "https://sharplinebucket.s3.amazonaws.com/product-images/";

        if (ProductIds != null && ProductMainImages != null) {
            String[] ids = ProductIds.split(",");
            String[] images = ProductMainImages.split(",");

            for (int i = 0; i < images.length; i++) {
                String productId = (i < ids.length) ? ids[i] : "unknown";
                String filename = images[i];
                String fullUrl = baseUrl + productId + "/" + filename;
                SortedMainImage.add(fullUrl);
            }
        }
        return SortedMainImage;
    }


    public List<String> getSortedCategoryNames() {

        SortedCategoryNames=new ArrayList<>();
        if( CategoryNames!=null)
        {
            String [] splitCategoryName=CategoryNames.split(",");
            if(splitCategoryName.length>0){
                for(String s:splitCategoryName){
                    SortedCategoryNames.add(s);
                }
            }
        }
        return SortedCategoryNames;
    }

    public List<String> getSortedDetailproductCosts() {

        SortedDetailproductCosts=new ArrayList<>();
        if( DetailproductCosts!=null)
        {
            String [] splitDetailCost=DetailproductCosts.split(",");
            if(splitDetailCost.length>0){
                for(String s:splitDetailCost){
                    SortedDetailproductCosts.add(s);
                }
            }
        }
        return SortedDetailproductCosts;
    }

    public List<String> getSortedProductId() {

        SortedProductId=new ArrayList<>();
        if( ProductIds!=null)
        {
            String [] splitProductId=ProductIds.split(",");
            if(splitProductId.length>0){
                for(String s:splitProductId){
                    SortedProductId.add(s);
                }
            }
        }
        return SortedProductId;
    }

    public List<String> getSortedproductCosts() {

        SortedproductCosts=new ArrayList<>();
        if( ProductCosts!=null)
        {
            String [] splitProductCost=ProductCosts.split(",");
            if(splitProductCost.length>0){
                for(String s:splitProductCost){
                    SortedproductCosts.add(s);
                }
            }
        }
        return SortedproductCosts;
    }

    public List<String> getSortedShippingCosts() {

        SortedShippingCosts=new ArrayList<>();
        if( ShippingCosts!=null)
        {
            String [] splitShippingCost=ShippingCosts.split(",");
            if(splitShippingCost.length>0){
                for(String s:splitShippingCost){
                    SortedShippingCosts.add(s);
                }
            }
        }
        return SortedShippingCosts;
    }
}
