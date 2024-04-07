package com.example.demo01.src.Controller;

import com.example.demo01.src.Configuration.Utils.FileUploadUtil;
import com.example.demo01.src.Pojo.Brand;
import com.example.demo01.src.Pojo.BrandCategoryName;
import com.example.demo01.src.Pojo.Category;
import com.example.demo01.src.Service.BrandService;
import com.example.demo01.src.Service.CategoryService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@Log
public class BrandController {

    @Autowired
    BrandService brandService;

    @Autowired
    CategoryService categoryService;




    @GetMapping("/brands/{pageno}")
    public String getBrandList(Model model, @PathVariable int pageno) {
        List<Brand> list = new ArrayList<>();
        list = brandService.getBrandByPagination(pageno);
        List<BrandCategoryName> categoryNames = new ArrayList<>();
        categoryNames = brandService.getCategoryName();
        List<Integer> totallist = new ArrayList<>();
        totallist = categoryService.getPageCount();
        model.addAttribute("currentPage", pageno);
        model.addAttribute("totalpage", totallist);
        for (int i = 0; i < categoryNames.size(); i++) {
            String name = categoryNames.get(i).getName();

            for (int j = 0; j < list.size(); j++) {
                if (i == j) {
                    Brand b = list.get(i);
                    b.setParentname(name);
                }
                model.addAttribute("list", list);
            }
        }

        return "brandList";
    }

    @PostMapping("/brands/1/keyword")
    public String getfilteredlist(Model model, @RequestParam("keyword") String keyword) {
        //user use filter function to select list
        List<Brand> filteredList = new ArrayList<>();
        filteredList = brandService.getFilterByKeyword(keyword);
        List<BrandCategoryName> categoryNames = new ArrayList<>();
        categoryNames = brandService.getCategoryName();
        for (int i = 0; i < categoryNames.size(); i++) {
            String name = categoryNames.get(i).getName();

            for (int j = 0; j < filteredList.size(); j++) {
                if (i == j) {
                    Brand b = filteredList.get(i);
                    b.setParentname(name);
                }
                model.addAttribute("list", filteredList);
            }
        }

        return "brandList";
    }

    @GetMapping("/brands/new")
    public String displayBForm(Model model) {
        List<Category> list = categoryService.getallList();
        Brand brand = new Brand();
        model.addAttribute("brand", brand);
        model.addAttribute("clist", list);
        return "BrandForm";
    }

    @PostMapping("/brand/save")
    public String insertBrand(@ModelAttribute("brand") Brand brand, HttpSession session, @RequestParam("photo") MultipartFile multipartFile) {
        String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        brand.setLogo(filename);
        brandService.saveBrand(brand);
        Brand b = brandService.getBrandIdByName(brand.getName());
        Integer bid = b.getId();
        ;
        String cname = brand.getParentname();
        log.info("cname {}"+cname);
        Integer cid = Integer.parseInt(cname);
        brandService.createBrandCategory(bid, cid);

        if (!multipartFile.isEmpty()) {
            String uploadDir = "brand_logo" + File.separator + b.getId();
            try {
                FileUploadUtil.saveFile(uploadDir, filename, multipartFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String message = "The brand has been saved successfully!";
        session.setAttribute("message", message);

        return "redirect:/brands/1";

    }

    @GetMapping("/brand/edit/{id}")
    public String gotoBrandEdit(@PathVariable int id, Model model) {
        List<Category> list = categoryService.getallList();
        Brand brand = brandService.selectBrandById(id);
        model.addAttribute("brand", brand);
        model.addAttribute("clist", list);
        return "brand_edit";
    }

    @PostMapping("/brand/edit")
    public String EdtBrand(@ModelAttribute("brand") Brand brand, @RequestParam("photo") MultipartFile multipartFile) throws IOException {
        String newimg = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        if (!multipartFile.isEmpty()) {
            String uploadDir = "brand-image" + File.separator + brand.getId();
            FileUploadUtil.saveFile(uploadDir, newimg, multipartFile);
        }
        brand.setLogo(newimg);
        brandService.editBrandById(brand);

        return "redirect:/brands/1";
    }

    @GetMapping("/brand/delete/{id}")
    public String deleteBrandById(@PathVariable int id) {
        brandService.deleteBrandById(id);
        return "redirect:/brands/1";
    }
}
