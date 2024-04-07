package com.example.demo01.src.Controller;

import com.example.demo01.src.Configuration.Exporter.CategoryCsvExporter;
import com.example.demo01.src.Configuration.Utils.FileUploadUtil;
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
    public String newCategoryList(@ModelAttribute Category category, HttpSession session, @RequestParam("photo") MultipartFile multipartFile) {
        String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        category.setImage(filename);
        Category cgo = new Category();
        String parentname = category.getParentname();
        Integer parentid = Integer.parseInt(parentname);
        category.setParentid(parentid);
        categoryService.saveCategory(category);
        cgo = categoryService.getcategoryByName(category.getName());
        if (!multipartFile.isEmpty()) {
            String uploadDir = "category-image" + File.separator + cgo.getId();
            try {
                FileUploadUtil.saveFile(uploadDir, filename, multipartFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String message = "The category has been saved successfully!";
        session.setAttribute("message", message);
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
    public String updateCategoryList(@ModelAttribute Category category, @RequestParam("photo") MultipartFile multipartFile) throws IOException {


        String newimg = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        if (!multipartFile.isEmpty()) {
            String uploadDir = "./category-image" + File.separator + category.getId();
            FileUploadUtil.saveFile(uploadDir, newimg, multipartFile);
        }
        category.setImage(newimg);
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
        return "redirect:/categories/1";
    }

    @GetMapping("/categories/export/csv")
    public void exportCategoryToCsv(HttpServletResponse response) throws IOException {
        List<Category> list = categoryService.getallList();
        CategoryCsvExporter categoryCsvExporter = new CategoryCsvExporter();
        categoryCsvExporter.export(list, response);

    }
}
