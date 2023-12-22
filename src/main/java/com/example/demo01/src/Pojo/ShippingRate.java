package com.example.demo01.src.Pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class ShippingRate {


    @ApiModelProperty(notes = "The shippingRate ID")
    private int id;
    @ApiModelProperty(notes = "The shipping country")
    private int country_id;
    @ApiModelProperty(notes = "The shipping country's state")
    private String state;
    @ApiModelProperty(notes = "The shipping rate")
    private float rate;
    @ApiModelProperty(notes = "The shipping days")
    private int days;
    @ApiModelProperty(notes = "Support COD")
    private int CodSupported;
}
