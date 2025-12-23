package com.example.demo01.src.Controller;

import com.example.demo01.src.Configuration.Exporter.CategoryCsvExporter;
import com.example.demo01.src.Configuration.Utils.FileUploadUtil;
import com.example.demo01.src.Pojo.AmazonS3Util;
import com.example.demo01.src.Pojo.Category;
import com.example.demo01.src.Service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class CategoryController {



    private final CategoryService categoryService;

    private final AmazonS3Util amazonS3Util;


    @GetMapping("/categories/{pageno}")
    public String getallCategoryList(@PathVariable int pageno, Model model) {

        List<Category> list = new ArrayList<>();
        list = categoryService.getCategoryByPagination(pageno);
        List<Integer> totallist = new ArrayList<>();
        totallist = categoryService.getPageCount();
        model.addAttribute("currentPage", pageno);
        model.addAttribute("totalpage", totallist);
        model.addAttribute("clist", list);
        return "Category";
    }

    @GetMapping("/categories/new")
    public String openCategoryList(Model model) {
        List<Category> list = new ArrayList<>();
        list = categoryService.GetHierarchicalCategories();
        Category category=new Category();
        model.addAttribute("category",category);
        model.addAttribute("clist", list);
        return "CategoryForm";
    }

    @PostMapping("/category/save")
    public String newCategoryList(@ModelAttribute Category category,
                                  HttpSession session,
                                  @RequestParam("photo") MultipartFile multipartFile) {

        try {
            category.setParentid(Integer.parseInt(category.getParentname()));
        } catch (NumberFormatException e) {
            session.setAttribute("message", "Invalid parent category ID");
            return "redirect:/categories/1";
        }

        // 先存一次拿到 ID
        categoryService.saveCategory(category);

        if (multipartFile != null && !multipartFile.isEmpty()) {
            String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename())
                    .replaceAll(" ", "_");
            category.setImage(filename);

            String uploadDir = "categories-images/" + category.getId(); // 注意：S3 key 用 /

            try (var is = multipartFile.getInputStream()) {

                amazonS3Util.removeFolder(uploadDir);

                amazonS3Util.uploadFile(uploadDir, filename, is, multipartFile.getSize());
                log.info("Uploading to: {}/{}", uploadDir, filename);

            } catch (IOException e) {
                session.setAttribute("message", "Failed to upload category image.");
                return "redirect:/categories/1";
            }

            categoryService.saveCategory(category);
        }

        session.setAttribute("message", "The category has been saved successfully!");
        return "redirect:/categories/1";
    }


    @RequestMapping("/category/edit/{id}")
    public String editCategoryList(@PathVariable Integer id, Model model) {
        List<Category> list = new ArrayList<>();
        Category category = categoryService.getCategoriesById(id);
        model.addAttribute("list", category);
        return "CategoryEdit";

    }

    @PostMapping("/categories/update")
    public String updateCategoryList(
            @ModelAttribute Category category,
            @RequestParam("photo") MultipartFile multipartFile) {

        try {
            if (multipartFile != null && !multipartFile.isEmpty()) {

                String newImage = StringUtils.cleanPath(multipartFile.getOriginalFilename())
                        .replaceAll("\\s+", "_");

                String uploadDir = "categories-images/" + category.getId();

                amazonS3Util.removeFolder(uploadDir);

                // 2) 上傳新檔
                try (InputStream is = multipartFile.getInputStream()) {
                    amazonS3Util.uploadFile(uploadDir, newImage, is, multipartFile.getSize());
                }

                category.setImage(newImage);
                log.info("category's image name: {}", newImage);

            } else {

                Category existing = categoryService.getCategoriesById(category.getId());
                category.setImage(existing.getImage());
            }

            categoryService.UpdateCategory(category);
            return "redirect:/categories/1";

        } catch (Exception e) {
            log.error("Failed to update category image, categoryId={}", category.getId(), e);
            return "redirect:/categories/1"; // 你也可以導到 error page
        }
    }


    @RequestMapping("/category/update/enabled/{id}/{Permission}")
    public String updateCategoryEnabledStatus(@PathVariable int id, @PathVariable int Permission) {
        if (1==(Permission)) {
            categoryService.UpdateEnabledStatus(id, 0);
        } else if (0 ==(Permission)) {
            categoryService.UpdateEnabledStatus(id, 1);
        } else {
            // Handle invalid input, such as redirecting to an error page or logging a message.
            // For now, let's log an error message:
           log.info("Invalid value provided for enabled status: " + Permission);
        }
        return "redirect:/categories/1";
    }


    @RequestMapping("category/delete/{id}")
    public String deleteCategoryById(@PathVariable int id) {
        categoryService.deleteCategoryById(id);
        String fileName="categories-images/"+id;
        //Deleting the specific category's image after removing it from the database.
        amazonS3Util.deleteFile(fileName);
        return "redirect:/categories/1";
    }

    @GetMapping("/categories/export/csv")
    public void exportCategoryToCsv(HttpServletResponse response) throws IOException {
        List<Category> list = categoryService.getallList();
        CategoryCsvExporter categoryCsvExporter = new CategoryCsvExporter();
        categoryCsvExporter.export(list, response);

    }
}
