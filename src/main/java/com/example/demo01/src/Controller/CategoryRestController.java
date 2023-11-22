package com.example.demo01.src.Controller;

import com.example.springboot_ecommerce.Pojo.Category;
import com.example.springboot_ecommerce.Pojo.Users;
import com.example.springboot_ecommerce.Service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CategoryRestController {

    @Autowired
    CategoryService categoryService;

    private final static Logger logger = LoggerFactory.getLogger(CategoryRestController.class);

    @RequestMapping(value = "/checkunique", method = RequestMethod.POST)
    public String checkUnique(@RequestBody Category category) {
        System.out.println(category.getName());
        boolean unique = categoryService.checkUnique(category.getName());
        return unique ? "OK" : "DuplicatedName";

    }
}
