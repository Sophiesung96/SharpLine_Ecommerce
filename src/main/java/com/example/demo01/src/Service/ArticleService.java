package com.example.demo01.src.Service;

import com.example.demo01.src.Pojo.Article;

import java.util.List;

public interface ArticleService {

    List<Article> getAllList();
    Article editArticleById(int id);
    void saveCreatedArticle(Article article);
    void updateEditedArticleById(Article article);
    List<Article> searchByKeyword(String keyword);
    void deleteArticleById(int id);
}
