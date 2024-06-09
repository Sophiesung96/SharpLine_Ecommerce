package com.example.demo01.src.DAO;

import com.example.demo01.src.Pojo.Article;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ArticleDAOImplTest {

    @SpyBean
    ArticleDAO articleDAO;


    @Test
    public void testGetArticleList(){
        List<Article> list=articleDAO.getAllList();
        assertNotNull(list);
        list.stream().forEach(article->assertTrue(article.getId()>0));
    }

    @Test
    public void testSearchByKeyword(){
        List<Article> list=articleDAO.searchByKeyword("about");
        assertNotNull(list);
        list.stream().forEach(article->assertTrue(article.getId()>0));
    }

}