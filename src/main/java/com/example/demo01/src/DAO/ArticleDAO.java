package com.example.demo01.src.DAO;

import com.example.demo01.src.Pojo.Article;

import java.util.List;

public interface ArticleDAO {

    List<Article> getAllList();
    Article editArticleById(int id);
    void updateEditedArticleById(Article article);
    void saveCreatedArticle(Article article);
    List<Article> searchByKeyword(String keyword);
    void deleteArticleById(int id);
}
