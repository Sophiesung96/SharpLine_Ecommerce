package com.example.demo01.src.Controller;

import com.example.demo01.src.Exception.MyExceptionHandler;
import com.example.demo01.src.Pojo.ShippingRate;
import com.example.demo01.src.Service.ShippingRateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShippingRateRestController {

    @Autowired
    ShippingRateService shippingRateService;





    @PostMapping("/get_shipping_cost")
    @Operation(summary = "Get Shipping Cost", description = "Calculate and Get Shipping Cost")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(),
                    mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Cannot found the data",
                    content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema()) }) })
    public ResponseEntity<String> getShippingCost(@RequestBody ShippingRate shippingRate){
     float cost=shippingRateService.calculateShippingCost(shippingRate.getProductId(), shippingRate.getCountry_id(), shippingRate.getState());
       return ResponseEntity.status(HttpStatus.ACCEPTED).body(String.valueOf(cost));
    }
}
