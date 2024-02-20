package com.example.demo01.src.Controller;

import com.example.demo01.src.Pojo.Customer;
import com.example.demo01.src.Pojo.Review;
import com.example.demo01.src.Service.CustomerService;
import com.example.demo01.src.Service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ReviewController {
    @Autowired
    ReviewService reviewService;

    @Autowired
    CustomerService customerService;

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
}
