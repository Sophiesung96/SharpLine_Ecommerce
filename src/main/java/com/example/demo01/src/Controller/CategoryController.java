package com.example.demo01.src.Controller;

import com.example.demo01.src.Configuration.Exporter.CategoryCsvExporter;
import com.example.demo01.src.Configuration.Utils.FileUploadUtil;
import com.example.demo01.src.Pojo.AmazonS3Util;
import com.example.demo01.src.Pojo.Category;
import com.example.demo01.src.Service.CategoryService;
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
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class CategoryController {


    @Autowired
    CategoryService categoryService;


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
    public String newCategoryList(
            @ModelAttribute Category category,
            HttpSession session,
            @RequestParam("photo") MultipartFile multipartFile) {

        // Convert parentname (assumed to be String) into an integer ID
        try {
            Integer parentId = Integer.parseInt(category.getParentname());
            category.setParentid(parentId);
        } catch (NumberFormatException e) {
            session.setAttribute("message", "Invalid parent category ID");
            return "redirect:/categories/1";
        }

        // First save the category to generate its ID (if new)
        categoryService.saveCategory(category);

        // Only handle the image upload if a file is selected
        if (!multipartFile.isEmpty()) {
            String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename())
                    .replaceAll(" ", "_");
            category.setImage(filename);  // Set image name in DB

            String uploadDir = "categories-images/" + category.getId();

            try {

                // Upload the new image to S3
                AmazonS3Util.uploadFile(uploadDir, filename, multipartFile.getInputStream());

                log.info("Uploading to:{} ",uploadDir + "/ {}",filename);


            } catch (IOException e) {
                e.printStackTrace();
                session.setAttribute("message", "Failed to upload category image.");
                return "redirect:/categories/1";
            }

            // Save the category again to persist the image name
            categoryService.saveCategory(category);
        }

        // Add success message to session
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
            @RequestParam("photo") MultipartFile multipartFile) throws IOException {

        if (!multipartFile.isEmpty()) {
            // Clean and extract new image file name
            String newImage = StringUtils.cleanPath(multipartFile.getOriginalFilename())
                    .replaceAll(" ", "_");

            // Upload the new image to the server or cloud storage
            String uploadDir = "categories-images/" + category.getId();
            AmazonS3Util.removeFolder(uploadDir); // Optional: clear old logo
            // Upload the new image to S3
            AmazonS3Util.uploadFile(uploadDir, newImage, multipartFile.getInputStream());

            // Update the image field only if a new file was uploaded
            category.setImage(newImage);
            log.info("category's image name:{}",newImage);
        } else {
            // If no new file is uploaded, preserve the existing image
            Category existing = categoryService.getCategoriesById(category.getId());
            category.setImage(existing.getImage());
        }

        categoryService.UpdateCategory(category);
        return "redirect:/categories/1";
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
        AmazonS3Util.deleteFile(fileName);
        return "redirect:/categories/1";
    }

    @GetMapping("/categories/export/csv")
    public void exportCategoryToCsv(HttpServletResponse response) throws IOException {
        List<Category> list = categoryService.getallList();
        CategoryCsvExporter categoryCsvExporter = new CategoryCsvExporter();
        categoryCsvExporter.export(list, response);

    }
}
