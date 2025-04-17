package com.example.demo01.src.DAO;

import com.example.demo01.src.Mapper.ArticleMapper;
import com.example.demo01.src.Pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ArticleDAOImpl implements  ArticleDAO{

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Article> getAllList() {
        String sql="select * from Article";
        Map<String,Object> map=new HashMap<>();
        List<Article> list=namedParameterJdbcTemplate.query(sql,map,new ArticleMapper());
        if(list.size()>0){
            return list;
        }
        return null;
    }

    @Override
    public Article editArticleById(int id) {
        String sql="select * from Article where article_id=:id";
        Map<String,Object> map=new HashMap<>();
        map.put("id",id);
        List<Article> list=namedParameterJdbcTemplate.query(sql,map,new ArticleMapper());
        if(list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public void updateEditedArticleById(Article article) {
        String sql="update Article set title=:title, Type=:type, created_By=:createdby,updated_time=updatedtime, published=:publish, alias=:alias, content=:content where article_id=:id";
        Map<String,Object> map=new HashMap<>();
        map.put("id",article.getId());
        map.put("title",article.getId());
        map.put("type",article.getId());
        map.put("createdby",article.getId());
        map.put("updatedtime",article.getId());
        map.put("publish",article.getId());
        map.put("content",article.getContent());
        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public void saveCreatedArticle(Article article) {
        String sql="insert into Article(title,Type,created_By,updated_time,published,content) values(:title,:type,:createdby,:updatetime,:publish,:content)";
        Map<String,Object> map=new HashMap<>();
        map.put("title",article.getTitle());
        map.put("type",article.getType());
        map.put("createdby",article.getCreatedBy());
        map.put("updatetime",article.getUpdatedTime());
        map.put("publish",article.getPublished());
        map.put("content",article.getContent());
        KeyHolder keyHolder=new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);
    }

    @Override
    public List<Article> searchByKeyword(String keyword) {
        String sql="select * from Article where title  LIKE CONCAT('%', :keyword, '%')";
        Map<String,Object> map=new HashMap<>();
        map.put("keyword",keyword);
        List<Article> list=namedParameterJdbcTemplate.query(sql,map,new ArticleMapper());
        if(list.size()>0){
            return list;
        }
        return null;
    }

    @Override
    public void deleteArticleById(int id) {
        String sql="delte from Article where id=:id";
        Map<String,Object> map=new HashMap<>();
        map.put("id",id);
        namedParameterJdbcTemplate.update(sql,map);
    }

}
