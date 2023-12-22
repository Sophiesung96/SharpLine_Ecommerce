package com.example.demo01.src.Controller;

import com.example.demo01.src.Configuration.JWTUtil;
import com.example.demo01.src.Pojo.Category;
import com.example.demo01.src.Service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@Slf4j
public class HomeAdminController {
     @Autowired
    CategoryService categoryService;



    @RequestMapping("/home")
    public String homepage(HttpServletResponse response) {

        return "homepage";
    }


    @RequestMapping("/index")
    public String test(Model model,HttpServletRequest request) {
        List<Category> list=categoryService.getallList();
        model.addAttribute("list",list);
        return "index";
    }
}
