package com.example.demo01.src.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private int id;
    private int customerId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String addressline1;
    private String addressline2;
    private String city;
    private String state;
    private int countryId;
    private String postalCode;
    private int defaultAddress;


    public String getAddress() {
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


    public int isDefaultforShopping(){
        return this.defaultAddress;
    }





}
