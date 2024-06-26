package com.example.demo01.src.Controller;

import com.example.demo01.src.Pojo.Category;
import com.example.demo01.src.Pojo.Users;
import com.example.demo01.src.Service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    @Operation(description = "check whether the category's name is unique or not")
    public String checkUnique(@RequestBody Category category) {
        System.out.println(category.getName());
        boolean unique = categoryService.checkUnique(category.getName());
        return unique ? "OK" : "DuplicatedName";

    }
}
