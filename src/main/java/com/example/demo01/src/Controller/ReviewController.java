package com.example.demo01.src.Controller;

import com.example.demo01.src.Configuration.MailConfiguration;
import com.example.demo01.src.Configuration.Utils.ControllerHelper;
import com.example.demo01.src.Exception.CustomerNotFoundException;
import com.example.demo01.src.Exception.ProductNotFoundException;
import com.example.demo01.src.Pojo.Customer;
import com.example.demo01.src.Pojo.Product;
import com.example.demo01.src.Pojo.Review;
import com.example.demo01.src.Service.CustomerService;
import com.example.demo01.src.Service.ProductService;
import com.example.demo01.src.Service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@Slf4j
public class ReviewController {
    @Autowired
    ReviewService reviewService;

    @Autowired
    CustomerService customerService;

    @Autowired
    ProductService productService;

    @Autowired
    ControllerHelper controllerHelper;




    @GetMapping("/review")
    public String showReviewList(Model model){
        List<Review> list=new ArrayList<>();
        list=reviewService.getAllReviewListWithCustomerFullName();
        model.addAttribute("list",list);

        return "ReviewList";
    }
    @GetMapping("/review/edit/{id}")
    public String ShowEditReviewForm(@PathVariable int id,Model model){
        Review review=reviewService.getEditReviewById(id);
        model.addAttribute("review",review);
         return "ReviewEditForm";
    }

    @PostMapping("/Edit/review")
    public String EditReview(@ModelAttribute Review review, RedirectAttributes rs){
          reviewService.EditReviewById(review);
          rs.addFlashAttribute("message","You have successfully edited the review!");
        return "redirect:/review";
    }

    @GetMapping("/review/detail/{id}")
    public String ExamineReviewDetail(@PathVariable int id,Model model){
        Review review=reviewService.getReviewDetailById(id);
        model.addAttribute("review",review);
        return "Review_detail_form";
    }

    @GetMapping("/review/customers/detail/{id}")
    public String getCustomerDetailForReview(@PathVariable int id, Model model){
            Customer customer = customerService.findCustomerById(id);
            Customer newcustomer = customerService.findCountryPerCustomerById(id);
            model.addAttribute("customer", customer);
            model.addAttribute("newcustomer", newcustomer);
            return "customer_detail_form";
    }
    @DeleteMapping("/review/delete/{id}")
    public String DeleteReviewById(@PathVariable int id){
        reviewService.getReviewDetailById(id);
        return "redirect:/review";
    }

    @GetMapping("/ratings/{productAlias}/page/{pageNo}")
    public String getRatingnReviewByProductPerPage(@PathVariable String productAlias, @PathVariable int pageNo,Model model){
        Product product=new Product();
        int currentPage=0;
        currentPage=pageNo;
        float averageRating=0.0f;
        try{
            product=productService.findByNickName(productAlias);

        }
        catch(ProductNotFoundException e){
          return "Error";
        }
        List<Review> ReviewList=reviewService.ListAllReviewListByPage(product,currentPage);
        log.info("producto:{}",product);
        log.info("reviewList:{}",ReviewList==null);
        if(ReviewList!=null){
            for(Review review:ReviewList){
                averageRating=review.getAverageRating();
                model.addAttribute("averageRating",averageRating);
            }
        }
        model.addAttribute("reviewList",ReviewList);
        model.addAttribute("product",product);
        model.addAttribute("currentPage",currentPage);
        return "reviewsProduct";
    }

    @GetMapping("/ratings/{productAlias}")
    public String listReviewByProductFirstPage(@PathVariable String productAlias,Model model){
        return getRatingnReviewByProductPerPage(productAlias,1,model);
    }
    @GetMapping("/write_review/product/{ProductId}")
    public String showReviewForm(@PathVariable int  ProductId, Model model, HttpServletRequest request){
        Review review=new Review();
        try{
        Product product=productService.findById(ProductId);
        model.addAttribute("product",product);
        model.addAttribute("review",review);
        Customer customer=controllerHelper.getAuthenticatedCustomer(request);
        boolean customerCanReview=reviewService.canCustomerReviewProduct(product.getId(),customer.getId());
        boolean IsReviewBefore=reviewService.didCustomerReviewProductBefore(customer.getId(),product.getId());
            if(product==null){
                return "Error";

            }
            if(IsReviewBefore){
                model.addAttribute("IsReviewBefore",IsReviewBefore);
                //If customer hasn't reviewed before but has purchased the product.
            }else{
                if(customerCanReview){
                    model.addAttribute("customerCanReview",customerCanReview);
                }else{
                    model.addAttribute("NoReviewPermission",true);
                }

            }

        }
        catch(ProductNotFoundException e){
            e.printStackTrace();
            return "Error";
        }
        return "reviewForm";
    }
    @PostMapping("/post/review")
    public String saveNewlyCreatedReview(HttpServletRequest request,Model model,@ModelAttribute Review review){
        Customer customer=controllerHelper.getAuthenticatedCustomer(request);
        Product product=new Product();
        try{
            product=productService.findById(review.getProductId());
        }catch(ProductNotFoundException e){
            return "error";
        }
        review.setCustomerId(customer.getId());
        //Insert this review into database
        //Retrieving from database after creating a new one
        Review DBReview=reviewService.saveReview(review);

        log.info("Review is not null:{}",DBReview!=null);
        if(DBReview!=null){
            String rating= String.valueOf(DBReview.getRating());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH mm ss");
            Date reviewTime=DBReview.getReviewTime();
            String formatedReviewTime=sdf.format(reviewTime);
            model.addAttribute("rating",rating);
            model.addAttribute("reviewTime",formatedReviewTime);
        }
        model.addAttribute("Review",DBReview);
        model.addAttribute("product",product);
        return "review_Done";
    }









}
