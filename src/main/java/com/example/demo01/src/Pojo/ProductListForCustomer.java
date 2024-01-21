package com.example.demo01.src.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductListForCustomer {
    private int OrderId;
    private String productId;
    private String ProductName;
    private String MainImage;
    private String ProductCost;
    private String Quantity;
    private String Subtotal;
    private String Unitprice;
    private String ShippingCost;
    private List<String> SortedProductName;
    private List<String> SortedMainImage;
    private List<String> SortedSubtotal;
    private List<String> SortedUnitprice;
    private List<String> SortedQuantity;
    private List<String> SortedProductId;
    private List<String> SortedShippingCost;
    private List<String> SortedProductCost;

    public List<String> getSortedMainImage() {
       this.SortedMainImage=new ArrayList<>();
            if(this.MainImage==null){

                MainImage= "/image/default.jpeg";
            }
            else{
                if (this.productId != null) {
                    // Split the MainImage string into an array
                    String[] newImg = MainImage.split(",");

                    // Create lists for SortedProductId and SortedMainImage
                    this.SortedProductId = Arrays.asList(productId.split(","));
                    this.SortedMainImage = new ArrayList<>();
                    // Iterate through each productId in SortedProductId
                    for (int i = 0; i < SortedProductId.size(); i++){
                        for (int j = 0; j < newImg.length; j++) {
                            if (i == j) {
                                SortedMainImage.add("/product-images/" + SortedProductId.get(i) + "/" +  newImg[j]);
                            }
                        }
                    }
                }
            }

        return SortedMainImage;
    }
}
