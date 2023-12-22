package com.example.demo01.src.Service;

import com.example.demo01.src.Pojo.*;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class CheckOutServiceImpl implements CheckOutService {
    private static final int DIM_DIVISOR=139;

    @Override
    public CheckOutInfo preparecheckOut(List<CartItemPName> cartItems, ShippingRate shippingRate) {
        CheckOutInfo checkOutInfo = new CheckOutInfo();
        float productCost = calculateProductCost(cartItems);
        float productTotal=calculateproductTotal(cartItems);
        float shippingCostTotal=calculateShippingCostTotal(cartItems,shippingRate);
        float paymentTotal=productTotal+shippingCostTotal;
        checkOutInfo.setProductCost(productCost);
        checkOutInfo.setProductTotal(productTotal);
        checkOutInfo.setDeliverDays(shippingRate.getDays());
        checkOutInfo.setDeliverDate(checkOutInfo.getDeliverDays());
        checkOutInfo.setCodSupported(shippingRate.getCodSupported()==1);
        checkOutInfo.setShippingCostTotal(shippingCostTotal);
        checkOutInfo.setPaymentTotal(paymentTotal);
        return checkOutInfo;
    }
    //calculate the total of shipping Cost of the products in the shopping cart
    private float calculateShippingCostTotal(List<CartItemPName> cartItems, ShippingRate shippingRate) {
        float shippingCostTotal = 0.0f;

        for (CartItemPName cartItem : cartItems) {
            float dimWeight = (cartItem.getLength() * cartItem.getWidth() * cartItem.getHeight()) / DIM_DIVISOR;
            float finalWeight = cartItem.getWeight() > dimWeight ? cartItem.getWeight() : dimWeight;
            float cost = finalWeight * cartItem.getQuantity() * shippingRate.getRate();

            BigDecimal costBigDecimal = BigDecimal.valueOf(cost);
            // Rounding the cost to two decimal places
            BigDecimal roundedCost = costBigDecimal.setScale(2, RoundingMode.HALF_UP);

            // Set the rounded cost directly to the cart item
            cartItem.setShippingCost(roundedCost.floatValue());

            // Add the rounded cost to the total
            shippingCostTotal += roundedCost.floatValue();
        }

        return shippingCostTotal;
    }

    //calculate product total of the shopping cart
    private float calculateproductTotal(List<CartItemPName> cartItems) {
        float Total = 0.0f;
        for (CartItemPName cartItem : cartItems) {
            Total += cartItem.getSubTotal();
        }

        BigDecimal costBigDecimal = BigDecimal.valueOf(Total);
        // Rounding the cost to two decimal places
        BigDecimal roundedCost = costBigDecimal.setScale(2, RoundingMode.HALF_UP);
        // Converting the roundedCost back to a float
        float result = roundedCost.floatValue();
        return result;
    }
    //calculate all product prices of the shopping cart
    private float calculateProductCost(List<CartItemPName> cartItems) {
        float cost = 0.0f;
        for (CartItemPName cartItem : cartItems) {
            cost += cartItem.getQuantity() * cartItem.getDetailPrice();
        }
        BigDecimal costBigDecimal = BigDecimal.valueOf(cost);
        // Rounding the cost to two decimal places
        BigDecimal roundedCost = costBigDecimal.setScale(2, RoundingMode.HALF_UP);
        // Converting the roundedCost back to a float
        float result = roundedCost.floatValue();
        return result;
    }

}
