package com.example.demo01.src.Controller;

import com.example.demo01.src.Exception.ProductNotFoundException;
import com.example.demo01.src.Pojo.Product;
import com.example.demo01.src.Service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductRestController {

    @Autowired
    ProductService productService;

    @PostMapping("/productunique")
    @Operation(summary = "check product name",description = "check product's name is unique or not")
    public String checkProductNameUniqueness(@RequestBody Product product) {
        boolean y = productService.checkUniqueness(product);
        return y ? "OK" : "DuplicatedName";
    }
    @GetMapping("/products/get/{id}")
    @Operation(summary = "Get product additional info"
            ,description = "Get product's detailed info")
    public Product getProductInfo(@PathVariable int id) throws ProductNotFoundException {
        Product product=productService.findById(id);
        return product;
    }
}
