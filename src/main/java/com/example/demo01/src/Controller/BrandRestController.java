package com.example.demo01.src.Controller;

import com.example.demo01.src.Exception.CustomerNotFoundException;
import com.example.demo01.src.Exception.OrderNotFoundExcption;
import com.example.demo01.src.Exception.PayPalApiException;
import com.example.demo01.src.Pojo.Brand;
import com.example.demo01.src.Pojo.BrandCategoryName;
import com.example.demo01.src.Service.BrandService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BrandRestController {

    @Autowired
    BrandService brandService;

    @RequestMapping(value = "/checkBrandName", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the book",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Brand.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = { @Content(mediaType = "application/json"
                           ) }),
            @ApiResponse(responseCode = "404", description = "Brand not found",
                    content = { @Content(mediaType = "application/json")})})
    public String checkNameUnique(@RequestBody Brand brand) {
        boolean check = brandService.checkNameUnique(brand.getName());
        return check ? "true" : "false";
    }

    @GetMapping(("/brands/{id}/categories"))
    public List<BrandCategoryName> listCategoriesByName( @Parameter(description = "Brand Id number")@PathVariable int id) {
        List<BrandCategoryName> list = new ArrayList<>();
        try {
            list = brandService.getCategoryById(id);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return list;
    }
}
