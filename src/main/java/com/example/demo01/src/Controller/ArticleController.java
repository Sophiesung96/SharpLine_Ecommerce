package com.example.demo01.src.Controller;

import com.example.demo01.src.DAO.ArticleDAO;
import com.example.demo01.src.Pojo.Article;
import com.example.demo01.src.Service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class ArticleController {

    @Autowired
    ArticleService articleService;


    @GetMapping("/article")
    public String displayArticleList(@RequestParam(required = false) String keyword, Model model) {
        List<Article> list;
        if (keyword != null && !keyword.isEmpty()) {
            list = articleService.searchByKeyword(keyword);
        } else {
            list = articleService.getAllList();
        }
        model.addAttribute("list", list);
        return "ArticleList";
    }

    @GetMapping("/article/keyword")
    public String searchByKeyWord(@RequestParam String keyword, RedirectAttributes rs) {
        rs.addAttribute("keyword", keyword);
        return "redirect:/article";
    }

    @GetMapping("/article/edit/{id}")
    public String editArticleById(@PathVariable int id,Model model)
    {
        Article article=articleService.editArticleById(id);
        model.addAttribute("article",article);
        return "Article_edit";
    }

    @GetMapping("/article/create")
    public String createNewArticle(Model model){
        Article article=new Article();
        model.addAttribute("article",article);
        return "Article_creation";
    }

    @GetMapping("/article/save")
    public String saveNewlyCreatedArticle(@ModelAttribute Article article){
         articleService.saveCreatedArticle(article);
        return "redirect:/article";
    }

    @PostMapping("/article/update/edit")
    public String updateEditedArticle(@ModelAttribute Article article)
    {
        articleService.updateEditedArticleById(article);
        return "redirect:/article";
    }


    @GetMapping("/article/delete/{id}")
    public String deleteArticleById(@PathVariable int id){

        return "redirect:/article";
    }
    @GetMapping("/article/detail/{id}")
    public String examineArticleDetailById(@PathVariable int id){

        return "Article_creation";
    }

}
