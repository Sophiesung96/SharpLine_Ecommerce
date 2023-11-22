package com.example.demo01.src.Controller;

import com.example.springboot_ecommerce.Pojo.Category;
import com.example.springboot_ecommerce.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomeAdminController {
     @Autowired
    CategoryService categoryService;

    @RequestMapping("/home")
    public String homepage() {
        return "homepage";
    }


    @RequestMapping("/index")
    public String test(Model model) {
        List<Category> list=categoryService.getallList();
        model.addAttribute("list",list);
        return "index";
    }
}
