package com.example.demo01.src.Controller;

import com.example.demo01.src.Configuration.MailConfiguration;
import com.example.demo01.src.Configuration.Utils.ControllerHelper;
import com.example.demo01.src.Configuration.Utils.FileUploadUtil;
import com.example.demo01.src.Exception.CustomerNotFoundException;
import com.example.demo01.src.Pojo.*;
import com.example.demo01.src.Service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@Slf4j
public class ProductController {


    @Autowired
    ProductService productService;

    @Autowired
    BrandService brandService;

    @Autowired
    CategoryService categoryService;
    @Autowired
    ReviewService reviewService;
    @Autowired
    CustomerService customerService;
    @Autowired
    ControllerHelper controllerHelper;

    @Autowired
    ReviewVoteService reviewVoteService;


    @GetMapping("/products/{pageno}")
    public String listAll(Model model,@PathVariable int pageno) {
        List<Product> list = new ArrayList<>();
        list = productService.selectProductByPagination(pageno);
        List<ProductCBName> clist = new ArrayList<>();
        clist = productService.selectCategoyrnBrandByName();
        List<Integer> pagelist=new ArrayList<>();
        pagelist=productService.getPageCount();
        for (int i = 0; i < clist.size(); i++) {
            String categoryName = clist.get(i).getCategoryName();
            String brandName = clist.get(i).getBrandName();
            for (int j = 0; j < list.size(); j++) {
                if (i == j) {
                    Product p = list.get(j);
                    p.setCategoryName(categoryName);
                    p.setBrandName(brandName);
                }
                model.addAttribute("list", list);
                model.addAttribute("pagelist",pagelist);
                model.addAttribute("currentPage",pageno);
            }
        }

        return "Product";

    }

    @GetMapping("/products/new")
    public String newProduct(Model model) {
        List<Product> list = new ArrayList<>();
        list = productService.listAllOrderByName();
        List<Brand> blist = new ArrayList<>();
        blist = brandService.getallBrand();
        List<Category> clist = new ArrayList<>();
        clist = categoryService.getallList();
        Product product = new Product();
        product.setEnabled(1);
        product.setInStock(1);
        model.addAttribute("product", product);
        model.addAttribute("list", list);
        model.addAttribute("blist", blist);
        model.addAttribute("clist", clist);
        model.addAttribute("pageTitle", "Create Product");
        return "product_form";
    }

    @PostMapping("/products/save")
    public String createNewProduct(@ModelAttribute Product product, @RequestParam("fileImage") MultipartFile multipartFile,
                                   @RequestParam("extrapic") MultipartFile[] extraImageFile,
                                   @RequestParam(name = "detailNames", required = false) String[] detailNames,
                                   @RequestParam(name = "detailValues", required = false) String[] detailValues) throws IOException {
        try {
            if (product.getEnabled() !=1) {
                int enabled = 0;
                product.setEnabled(enabled);
            }
            if (product.getInStock() ==1) {
                int stock = 0;
                product.setInStock(stock);
            }
            //setting up main image that is uploaded by user
            setMainImage(multipartFile, product);
            //setting up extra images that are uploaded by user
            setExtraImage(extraImageFile, product);
            productService.saveProduct(product);
            List<Product> list = productService.selectProductByName(product);
            ProductDetail productDetail = new ProductDetail();
            Product p = list.get(0);
            //insert  product details into product_detail table
            List<ProductDetail> detailList = new ArrayList<>();
            detailList = setnSaveProductDetails(detailNames, detailValues, product);
            for (ProductDetail detail : detailList) {
                productService.saveProductDetails(detail, p.getId());
            }
            saveUploadImage(multipartFile, extraImageFile, p);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/products/1";
    }

   // this function is for creating product details for the first time
    private List<ProductDetail> setnSaveProductDetails(String[] detailNames, String[] detailValues, Product product) {
        if (detailNames == null || detailNames.length == 0) return null;
        List<ProductDetail> list = new ArrayList<>();
        for (int i = 0; i < detailNames.length; i++) {
            String name = detailNames[i];
            String value = detailValues[i];

          if (!name.isEmpty() &&!value.isEmpty()) {
                list.add(new ProductDetail(name, value));
            }
        }
        if(list.size()>0){
            return list;
        }
        return null;
    }

    private void setProductDetails(String [] detailId,String[] detailNames, String[] detailValues, Product product) {
        if (detailNames == null || detailNames.length == 0) return ;

        for (int i = 0; i < detailNames.length; i++) {
            String name = detailNames[i];
            String value = detailValues[i];
            int did = Integer.parseInt(detailId[i]);
            //already has product details
            if (did != 0) {
                ProductDetail newproductDetail = new ProductDetail(did,name,value);
                productService.UpdateProductDetails(newproductDetail, product);
            } else if (!name.isEmpty() && !value.isEmpty()) {
                ProductDetail newproductDetail = new ProductDetail(name, value);
                productService.saveProductDetails(newproductDetail, product.getId());
            }

        }
    }




    //save main image and extra images if there's any
    private void saveUploadImage(MultipartFile multipartFile, MultipartFile[] extraImagefile, Product p) throws IOException {
        // save product's MainImage
        String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        if (!multipartFile.isEmpty()) {

            String uploadDiv = "product-images" + File.separator+ p.getId();
            FileUploadUtil.saveFile(uploadDiv, filename, multipartFile);

        }
        // save product's ExtraImages
        if (extraImagefile.length > 0) {
            String extraDiv = "product-images/" + p.getId() + "/extras/";
            for (MultipartFile File : extraImagefile) {
                if (File.isEmpty()) continue;
                String Extrafilename = StringUtils.cleanPath(File.getOriginalFilename());
                FileUploadUtil.saveFile(extraDiv, Extrafilename, File);
                //save Extra image to product_image table
                productService.saveExtraImagesofProduct(Extrafilename, p);
            }
        }
    }

    private void setExtraImage(MultipartFile[] multipartFile, Product product) throws IOException {

        if (multipartFile.length > 0) {
            for (MultipartFile File : multipartFile) {
                if (!File.isEmpty()) {
                    String Extrafilename = StringUtils.cleanPath(File.getOriginalFilename());

                }

            }

        }
    }


    private void setMainImage(MultipartFile multipartFile, Product product) throws IOException {
        if(product.getMainimage()==null){
            if (!multipartFile.isEmpty()) {
                String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                product.setMainimage(filename);
            }
        }
        // if the edited product already has a main image but wants to change it
        // put the new main image into product
       else if (product.getMainimage()!=null){
            if (!multipartFile.isEmpty()) {
                String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
               if(!product.getMainimage().equals(filename)){
                   product.setMainimage(filename);
               }
            }
        }


    }

    @GetMapping("/update/enabled/{id}/{enabled}")
    public String updateCategoryEnabledStatus(@PathVariable int id, @PathVariable int enabled) {
        if (enabled == 1) {
            enabled = 0;
            productService.UpdateEnabledStatus(id, enabled);
        } else {
            enabled = 1;
            productService.UpdateEnabledStatus(id, enabled);
        }

        return "redirect:/products/1";
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable int id) {

        productService.deleteProductById(id);
        return "redirect:/products/1";
    }

    @GetMapping("/products/edit/{id}")
    public String editProduct(@PathVariable int id, Model model, HttpSession session) {
        try{
            Product product = productService.editProductById(id);
            //get all brand list
            List<Brand> list = brandService.getallBrand();
            model.addAttribute("product", product);
            model.addAttribute("brandList", list);
            model.addAttribute("pageTitle", "Edit Product Id:" + id);
            //get product's category and brand name
            ProductCBName productCBName = productService.selectCategoyrnBrandByProductId(id);
            model.addAttribute("productCBName", productCBName);
            //get extraImage List
            List<ProductImage> imageList = productService.selectExtraByProductId(id);
            model.addAttribute("pimage", imageList);
            Integer numberOfExistingExtraImages= imageList.size();
            model.addAttribute("numberOfExistingExtraImages",numberOfExistingExtraImages);

            List<ProductDetail> detailList = new ArrayList<>();
            //get product detail List
            detailList = productService.selectProductDetailsById(id);
            model.addAttribute("detailList", detailList);

        }catch (Exception e){
            e.printStackTrace();
        }

        return "product_edit";
    }

    // update edited product info
    @PostMapping("/product/updateProduct")
    public String saveEditedProduct(@ModelAttribute Product product, @RequestParam("mImage") MultipartFile multipartFile,
                                    @RequestParam("extrapic") MultipartFile[] extraImageFile,
                                    @RequestParam(name = "detailNames", required = false) String[] detailNames,
                                    @RequestParam(name = "detailValues", required = false) String[] detailValues,
                                    @RequestParam(name="did") String [] did) {

        try {
            if (product.getEnabled() ==null) {
                int enabled = 0;
                product.setEnabled(enabled);
            }
            if (product.getInStock() ==null) {
                int stock = 0;
                product.setInStock(stock);
            }
            //setting up updated Time at now
            product.setUpdatedTime(new Date());
            //setting up  edited main image that is uploaded by user
            setMainImage(multipartFile, product);
            //setting up  edited extra images that are uploaded by user
            setExtraImage(extraImageFile, product);
            productService.saveEditedProductById(product);
           setProductDetails(did,detailNames,detailValues,product);
            saveUploadImage(multipartFile, extraImageFile, product);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/products/1";
    }


    @RequestMapping(value={"/getremoveid"},method = {RequestMethod.POST})
    @ResponseBody
    public int getExtraImageId(@RequestBody ProductDetail productDetail){
        int id= productDetail.getId();
        int pid= productDetail.getProductId();
       log.info("pid:"+pid);
       log.info("id"+id);
       productService.DeleteProductDetailsByProductId(pid,id);
        return id;
    }


    @GetMapping("/products/detail/{id}")
    public String viewProductDetail(@PathVariable int id, Model model, HttpSession session) {
        Product product = productService.editProductById(id);
        Brand brand=brandService.selectBrandById(product.getBrandId());
        Category category=categoryService.getCategoriesById(product.getCategoryId());
        model.addAttribute("product", product);
        model.addAttribute("brand", brand);
        model.addAttribute("category", category);
        return "product_detail_form";
    }

    @GetMapping("/c/{name}/{page}")
    public String viewCategory(  @PathVariable int page,@PathVariable String name, Model m){
        try{
            log.info("category name:{}",name);
           Category category=categoryService.findByAliasEnabled(name);
            if(category==null){
                log.info("There's something wrong with this category");
                return "Error";
            }
            List<Integer> pagelist=new ArrayList<>();
            pagelist=productService.getPageCount();
            int currentpage=page;
            List<Category>categoryList=categoryService.listChildrenCategoreisByParentId(category.getId());
            m.addAttribute("nickname",name);
            m.addAttribute("pagelist",pagelist);
            m.addAttribute("currentpage",currentpage);
            m.addAttribute("category",category);
            m.addAttribute("categoryList",categoryList);
            List<Product>list= productService.getProductByCategoryId(category.getId(),page);
            m.addAttribute("plist",list);
        }
        catch (Exception e){
            e.printStackTrace();
        }

         return"ProductByCategory";
    }


    @GetMapping("/p/{product_nickname}/{pageno}")
    public String viewProductdetail(@PathVariable int pageno, Model model,@PathVariable String product_nickname,HttpServletRequest request)  {
        try{
            float averageRating = 0.0f;
            Product product= productService.findByNickName(product_nickname);
            List<Integer> pagelist=productService.getPageCount();
            ProductCBName productCBName=productService.selectCategoyrnBrandByProductId(product.getId());
            List<ProductDetail> detailList=productService.selectProductDetailsById(product.getId());
            List<ProductImage> extraList=productService.selectExtraByProductId(product.getId());
            //Get all reviews for the product
            List<Review> reviewList=reviewService.List3MostRecentReviews(product.getId());
            if(product==null){
                return "Error";

            }
            if(reviewList!=null){
                Customer customer=controllerHelper.getAuthenticatedCustomerForReviewVote(request);
                for(Review review:reviewList){
                    averageRating=review.getAverageRating();
                    model.addAttribute("reviewList",reviewList);
                }

                if(customer!=null){
                    boolean IsReviewBefore=reviewService.didCustomerReviewProductBefore(customer.getId(),product.getId());
                    if(IsReviewBefore){
                        log.info("IsReviewBefore:{}",IsReviewBefore);
                        model.addAttribute("IsReviewBefore",IsReviewBefore);
                    }else{
                        boolean customercanReview=reviewService.canCustomerReviewProduct(product.getId(),customer.getId());
                        log.info("customercanReview:{}",customercanReview);
                        model.addAttribute("customercanReview",customercanReview);
                    }
                }
            }
            model.addAttribute("product",product);
            model.addAttribute("averageRating",averageRating);
            model.addAttribute("nickname",product_nickname);
            model.addAttribute("currentpage",pageno);
            model.addAttribute("pagelist",pagelist);
            model.addAttribute("productCBName",productCBName);
            model.addAttribute("detailList",detailList);
            model.addAttribute("extraList",extraList);
            return "product_detail";
        }catch (Exception e){
            e.printStackTrace();
        }

        return"ProductByCategory";

    }



    @GetMapping("/p/keyword/{pageno}")
    public String filteredProductdetail(@PathVariable int pageno, Model model, HttpServletRequest request) {

       try{
           String keyword=request.getParameter("keyword");
           List<Integer>pagelist=productService.getFilteredPageCount(keyword);
           List<Product> list=productService.SearchByKeyword(pageno,keyword);
           boolean item;
           if(list!=null){
               item=true;
           }
           else{
               item=false;
           }
           model.addAttribute("item",item);
           model.addAttribute("keyword",keyword);
           model.addAttribute("list",list);
           model.addAttribute("currentpage",pageno);
           model.addAttribute("pagelist",pagelist);


           return"search_result";
       }catch(Exception e){
          e.printStackTrace();
       }
        return"ProductByCategory";

    }





}
