package com.example.demo01.src.Controller;

import com.example.demo01.src.Pojo.Brand;
import com.example.demo01.src.Pojo.BrandCategoryName;
import com.example.demo01.src.Service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BrandRestController {

    @Autowired
    BrandService brandService;

    @RequestMapping(value = "/checkBrandName", method = RequestMethod.POST)
    public String checkNameUnique(@RequestBody Brand brand) {
        boolean check = brandService.checkNameUnique(brand.getName());
        return check ? "true" : "false";
    }

    @GetMapping(("/brands/{id}/categories"))
    public List<BrandCategoryName> listCategoriesByName(@PathVariable int id) {
        List<BrandCategoryName> list = new ArrayList<>();
        try {
            list = brandService.getCategoryById(id);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return list;
    }
}
