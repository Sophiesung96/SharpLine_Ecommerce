package com.example.demo01.src.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShippingRate {

private int id;
private int country_id;
private String state;
private float rate;
private int days;
private int CodSupported;
}
