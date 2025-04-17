package com.example.demo01.src.Controller;

import com.example.demo01.src.Configuration.Utils.FileUploadUtil;
import com.example.demo01.src.Pojo.AmazonS3Util;
import com.example.demo01.src.Pojo.Brand;
import com.example.demo01.src.Pojo.BrandCategoryName;
import com.example.demo01.src.Pojo.Category;
import com.example.demo01.src.Service.BrandService;
import com.example.demo01.src.Service.CategoryService;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
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
    public String insertBrand(
            @ModelAttribute("brand") Brand brand,
            RedirectAttributes redirectAttributes,
            @RequestParam("photo") MultipartFile multipartFile) throws IOException {

        // Save brand first to get the generated ID
        brandService.saveBrand(brand);
        Brand savedBrand = brandService.getBrandIdByName(brand.getName());
        Integer brandId = savedBrand.getId();

        // Associate brand with category
        try {
            Integer categoryId = Integer.parseInt(brand.getParentname());  // Assuming parentname stores the category ID
            brandService.createBrandCategory(brandId, categoryId);
        } catch (NumberFormatException e) {
            log.error("Invalid category ID in brand.getParentname(): {}", brand.getParentname());
        }

        // Handle logo upload if file was selected
        if (!multipartFile.isEmpty()) {
            String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename())
                    .replaceAll(" ", "_");

            String uploadDir = "brand-logos/" + brandId;

            // Remove old logo folder (optional) and upload new one
            AmazonS3Util.removeFolder(uploadDir);
            AmazonS3Util.uploadFile(uploadDir, filename, multipartFile.getInputStream());

            // Update brand with logo filename and save again
            savedBrand.setLogo(filename);
            brandService.saveBrand(savedBrand);
        }

        redirectAttributes.addFlashAttribute("message", "The brand has been saved successfully!");
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
    public String editBrand(
            @ModelAttribute("brand") Brand brand,
            @RequestParam("photo") MultipartFile multipartFile) throws IOException {

        if (!multipartFile.isEmpty()) {
            // Clean file name
            String newImage = StringUtils.cleanPath(multipartFile.getOriginalFilename())
                    .replaceAll(" ", "_");

            // Upload to S3
            String uploadDir = "brand-logos/" + brand.getId();
            AmazonS3Util.removeFolder(uploadDir); // Optional: clear old logo
            AmazonS3Util.uploadFile(uploadDir, newImage, multipartFile.getInputStream());

            // Update logo only if file is uploaded
            brand.setLogo(newImage);
            log.info("Updated brand logo to: {}/{}", uploadDir, newImage);
        } else {
            // No new image uploaded – preserve existing one
            Brand existingBrand = brandService.getBrandIdByName(brand.getName());
            brand.setLogo(existingBrand.getLogo());
        }

        // Save changes
        brandService.editBrandById(brand);
        return "redirect:/brands/1";
    }


    @GetMapping("/brand/delete/{id}")
    public String deleteBrandById(@PathVariable int id,RedirectAttributes redirectAttributes) {
        brandService.deleteBrandById(id);
        String brandDir="brand-logos/"+id;
        AmazonS3Util.deleteFile(brandDir);
        redirectAttributes.addFlashAttribute("message","The Brand ID"+id+" has been deleted successfully");
        return "redirect:/brands/1";
    }
}
