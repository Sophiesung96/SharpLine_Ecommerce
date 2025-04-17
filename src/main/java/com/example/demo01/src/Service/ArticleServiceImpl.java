package com.example.demo01.src.Service;

import com.example.demo01.src.DAO.ArticleDAO;
import com.example.demo01.src.Pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements  ArticleService{
    @Autowired
    ArticleDAO articleDAO;

    @Override
    public List<Article> getAllList() {
        List<Article> list=articleDAO.getAllList();
        return list;
    }

    @Override
    public Article editArticleById(int id) {
        Article article=articleDAO.editArticleById(id);
        return article;
    }

    @Override
    public void saveCreatedArticle(Article article) {
        articleDAO.saveCreatedArticle(article);
    }

    @Override
    public void updateEditedArticleById(Article article) {
        articleDAO.updateEditedArticleById(article);
    }

    @Override
    public List<Article> searchByKeyword(String keyword) {
        List<Article> list=articleDAO.searchByKeyword(keyword);
        return list;
    }

    @Override
    public void deleteArticleById(int id) {
       articleDAO.deleteArticleById(id);
    }
}
