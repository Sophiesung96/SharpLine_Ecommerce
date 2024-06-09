package com.example.demo01.src.Mapper;

import com.example.demo01.src.Pojo.Article;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class ArticleMapper implements RowMapper<Article> {
    @Override
    public Article mapRow(ResultSet resultSet, int i) throws SQLException {
        Article article=new Article();
        article.setId(resultSet.getInt("article_id"));
        article.setTitle(resultSet.getString("title"));
        article.setCreatedBy(resultSet.getString("created_By"));
        article.setType(resultSet.getString("type"));
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date=dateFormat.format(resultSet.getTimestamp("updated_time"));
        article.setPublished(resultSet.getInt("published"));
        article.setUpdatedTime(date);
        article.setAlias(resultSet.getString("alias"));
        article.setContent(resultSet.getString("content"));
        return article;
    }
}
