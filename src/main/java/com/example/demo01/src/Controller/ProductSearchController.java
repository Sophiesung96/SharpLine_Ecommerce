package com.example.demo01.src.Controller;

import com.example.demo01.src.DAO.ProductDAO;
import com.example.demo01.src.Pojo.Product;
import com.example.demo01.src.Pojo.ProductCBName;
import com.example.demo01.src.Service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class ProductSearchController {
    @Autowired
    ProductService productService;

    @GetMapping("/orders/search_product")
    public String getSearchPage(Model model){
       int currentPage=1;
        model.addAttribute("currentpage",currentPage);
        return "Search_product";
    }

    @PostMapping("/orders/search_product")
    public String searchProduct(String keyword){

        return "redirect:/orders/search_product/page/1?keyword="+keyword;
    }

    @GetMapping("/orders/search_product/page/{pageno}")
    public String getSearchProductByPage(Model model, @PathVariable int pageno, @RequestParam(required = false) String keyword){
        List<Integer>pagelist=productService.getFilteredPageCount4Order(keyword);
        List<Product> list=productService.ProductSearchByKeywordforOrder(pageno,keyword);
        model.addAttribute("keyword",keyword);
        model.addAttribute("list",list);
        model.addAttribute("currentpage",pageno);
        model.addAttribute("pagelist",pagelist);

        return "Search_product";
    }



}
