package com.example.demo01.src.Controller;

import com.example.demo01.src.Pojo.Product;
import com.example.demo01.src.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductRestController {

    @Autowired
    ProductService productService;

    @PostMapping("/productunique")
    public String checkProductNameUniqueness(@RequestBody Product product) {
        boolean y = productService.checkUniqueness(product);
        return y ? "OK" : "DuplicatedName";
    }
}
