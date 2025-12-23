import com.example.demo01.src.Pojo.AmazonS3Util;
import com.example.demo01.src.Pojo.Brand;
import com.example.demo01.src.Pojo.BrandCategoryName;
import com.example.demo01.src.Pojo.Category;
import com.example.demo01.src.Service.BrandService;
import com.example.demo01.src.Service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;
    private final CategoryService categoryService;
    private final AmazonS3Util amazonS3Util;

    @GetMapping("/brands/{pageno}")
    public String getBrandList(Model model, @PathVariable int pageno) {

        List<Brand> list = brandService.getBrandByPagination(pageno);
        List<BrandCategoryName> categoryNames = brandService.getCategoryName();
        List<Integer> totallist = categoryService.getPageCount();

        model.addAttribute("currentPage", pageno);
        model.addAttribute("totalpage", totallist);


        int size = Math.min(list.size(), categoryNames.size());
        for (int i = 0; i < size; i++) {
            list.get(i).setParentname(categoryNames.get(i).getName());
        }

        model.addAttribute("list", list);
        return "brandList";
    }

    @PostMapping("/brands/1/keyword")
    public String getfilteredlist(Model model, @RequestParam("keyword") String keyword) {

        List<Brand> filteredList = brandService.getFilterByKeyword(keyword);
        List<BrandCategoryName> categoryNames = brandService.getCategoryName();

        int size = Math.min(filteredList.size(), categoryNames.size());
        for (int i = 0; i < size; i++) {
            filteredList.get(i).setParentname(categoryNames.get(i).getName());
        }

        model.addAttribute("list", filteredList);
        return "brandList";
    }

    @GetMapping("/brands/new")
    public String displayBForm(Model model) {
        List<Category> list = categoryService.getallList();
        model.addAttribute("brand", new Brand());
        model.addAttribute("clist", list);
        return "BrandForm";
    }

    @PostMapping("/brand/save")
    public String insertBrand(@ModelAttribute("brand") Brand brand,
                              RedirectAttributes redirectAttributes,
                              @RequestParam("photo") MultipartFile multipartFile) {

        try {
            // 1) 先存 brand（確保拿到 id）
            brandService.saveBrand(brand);

            // 你原本用 name 查回 brand id（可能重名），這裡假設 save 後 brand 已有 id
            Integer brandId = brand.getId();
            if (brandId == null) {
                // 如果你的 save 不會回填 id，那才用查詢，但建議改 service
                Brand savedBrand = brandService.getBrandIdByName(brand.getName());
                brandId = savedBrand != null ? savedBrand.getId() : null;
            }
            if (brandId == null) {
                redirectAttributes.addFlashAttribute("message", "Failed to save brand (no ID).");
                return "redirect:/brands/1";
            }

            // 2) 建 brand-category 關聯
            try {
                Integer categoryId = Integer.parseInt(brand.getParentname());
                brandService.createBrandCategory(brandId, categoryId);
            } catch (NumberFormatException e) {
                log.error("Invalid category ID in brand.getParentname(): {}", brand.getParentname());
            }

            // 3) 上傳 logo
            if (multipartFile != null && !multipartFile.isEmpty()) {
                String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename())
                        .replaceAll("\\s+", "_");

                String uploadPrefix = "brand-logos/" + brandId;

                // 想確保每個 brand 只留一張 logo：清 prefix
                amazonS3Util.removeFolder(uploadPrefix);

                try (var is = multipartFile.getInputStream()) {
                    amazonS3Util.uploadFile(uploadPrefix, filename, is, multipartFile.getSize());
                }

                brand.setLogo(filename);
                brandService.saveBrand(brand);
            }

            redirectAttributes.addFlashAttribute("message", "The brand has been saved successfully!");
            return "redirect:/brands/1";

        } catch (Exception e) {
            log.error("Failed to save brand", e);
            redirectAttributes.addFlashAttribute("message", "Failed to save brand.");
            return "redirect:/brands/1";
        }
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
    public String editBrand(@ModelAttribute("brand") Brand brand,
                            @RequestParam("photo") MultipartFile multipartFile,
                            RedirectAttributes redirectAttributes) {

        try {
            Brand existingBrand = brandService.selectBrandById(brand.getId());
            if (existingBrand == null) {
                redirectAttributes.addFlashAttribute("message", "Brand not found.");
                return "redirect:/brands/1";
            }

            if (multipartFile != null && !multipartFile.isEmpty()) {
                String newImage = StringUtils.cleanPath(multipartFile.getOriginalFilename())
                        .replaceAll("\\s+", "_");

                String uploadPrefix = "brand-logos/" + brand.getId();
                amazonS3Util.removeFolder(uploadPrefix);

                try (var is = multipartFile.getInputStream()) {
                    amazonS3Util.uploadFile(uploadPrefix, newImage, is, multipartFile.getSize());
                }

                brand.setLogo(newImage);
                log.info("Updated brand logo to: {}/{}", uploadPrefix, newImage);
            } else {
                // 沒上傳新圖：保留舊 logo
                brand.setLogo(existingBrand.getLogo());
            }

            brandService.editBrandById(brand);
            redirectAttributes.addFlashAttribute("message", "Brand updated successfully!");
            return "redirect:/brands/1";

        } catch (Exception e) {
            log.error("Failed to edit brand id={}", brand.getId(), e);
            redirectAttributes.addFlashAttribute("message", "Failed to update brand.");
            return "redirect:/brands/1";
        }
    }

    @GetMapping("/brand/delete/{id}")
    public String deleteBrandById(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            amazonS3Util.removeFolder("brand-logos/" + id);

            brandService.deleteBrandById(id);
            redirectAttributes.addFlashAttribute("message", "The Brand ID " + id + " has been deleted successfully");
        } catch (Exception e) {
            log.error("Failed to delete brand id={}", id, e);
            redirectAttributes.addFlashAttribute("message", "Failed to delete brand.");
        }
        return "redirect:/brands/1";
    }
}
