package com.example.demo01.src.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private Integer id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String addressline1;
    private String addressline2;
    private String city;
    private String state;
    private Integer countryId;
    private String postalCode;
    private Date  createdTime;
    private Date  updatedTime;
    private Integer enabled;
    private String verificationCode;
    private String countryCode;
    private String countryName;
    private String authenticationType;
    private String resetPasswordToken;


    public Customer(Integer id) {
        this.id = id;
    }

    public String getFullName(){
      return this.firstName+" "+this.lastName;
  }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

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

}
