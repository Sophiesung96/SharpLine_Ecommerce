package com.example.demo01.src.DAO;

import com.example.demo01.src.Mapper.CategoryMapper;
import com.example.demo01.src.Mapper.NestedCategoryMapper;
import com.example.demo01.src.Mapper.PageNumberMapper;
import com.example.demo01.src.Pojo.Category;
import com.example.demo01.src.Pojo.PageNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CategoryDAOImpl implements CategoryDAO {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public List<Category> getallCategories() {
        String sql = "select * from categories order by name asc";
        Map<String, Object> map = new HashMap<>();
        List<Category> list = new ArrayList<>();
        list = namedParameterJdbcTemplate.query(sql, map, new CategoryMapper());
        if (list.size() > 0) {
            return list;
        }
        return null;
    }

    @Override
    public void saveCategory(Category category) {
        String sql = "insert into categories(name,nickname,image,enabled,parent_id) value(:name, :nickname,:image,:enabled,:parentid)";
        Map<String, Object> map = new HashMap<>();
        map.put("name", category.getName());
        map.put("nickname", category.getNickname());
        map.put("image", category.getImage());
        map.put("enabled", category.getEnabled());
        map.put("parentid", category.getParentid());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
    }

    @Override
    public Category getcategoryByName(String name) {
        String sql = "select * from categories where name=:name";
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        List<Category> list = new ArrayList<>();
        list = namedParameterJdbcTemplate.query(sql, map, new CategoryMapper());
        if (list.size() > 0) {
            return list.get(0);
        } else {

        }
        return null;
    }

    @Override
    public Category getCategoriesById(int id) {
        String sql = "select * from categories where id=:id";
        Map<String, Object> map = new HashMap<>();
        List<Category> list = new ArrayList<>();
        map.put("id", id);
        list = namedParameterJdbcTemplate.query(sql, map, new CategoryMapper());
        if (list.size() > 0) {
            return list.get(0);

        }
        return null;
    }

    @Override
    public void UpdateCategory(Category category) {
        String sql = "update categories set name=:name, nickname=:nickname, image=:image,enabled=:enabled,parent_id=:parentid where id=:id";
        Map<String, Object> map = new HashMap<>();
        map.put("id", category.getId());
        map.put("name", category.getName());
        map.put("nickname", category.getNickname());
        map.put("image", category.getImage());
        map.put("category", category.getImage());
        map.put("enabled", category.getEnabled());
        map.put("parentid", category.getParentid());
        namedParameterJdbcTemplate.update(sql, map);

    }

    @Override
    public String SelectCategoryByName(String name) {
        String sql = "select image from categories where name=:name";
        String newimage = null;
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        List<Category> list = new ArrayList<>();
        list = namedParameterJdbcTemplate.query(sql, map, new CategoryMapper());
        for (Category category : list) {
            newimage = category.getImage();

        }
        return newimage;
    }


    @Override
    public Category findByName(String name) {
        String sql = "select * from categories where name=:name";
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        List<Category> list = new ArrayList<>();
        list = namedParameterJdbcTemplate.query(sql, map, new CategoryMapper());
        Category category = list.get(0);

        return category;
    }

    @Override
    public void UpdateEnabledStatus(int id, int enabled) {
        String sql = "update categories set enabled= 0 where id= :id";
        Map<String, Object> map = new HashMap<>();
       // map.put("enabled", enabled);
        map.put("id", id);
        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void deleteCategoryById(int id) {
        String sql = "delete from categories where id=:id";
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public List<Category> getCategoryByPagination(int Pageno) {
        String sql = "select * from categories limit :pageno,4";
        Map<String, Object> map = new HashMap<>();
        map.put("pageno", (Pageno - 1) * 4);
        List<Category> list = new ArrayList<>();
        list = namedParameterJdbcTemplate.query(sql, map, new CategoryMapper());
        if (list.size() > 0) {
            return list;
        } else {
            return null;
        }

    }

    @Override
    public Integer getPageCount() {
        String sql = "select count(*) as total from categories";
        Map<String, Object> map = new HashMap<>();
        List<PageNumber> list = new ArrayList<>();
        list = namedParameterJdbcTemplate.query(sql, map, new PageNumberMapper());
        int number = 0;
        for (PageNumber page : list) {
            number = page.getPagenumber();
        }
        return number;

    }

    @Override
    public Category findByAliasEnabled(String alias) {
        String sql="select * from categories where nickname=:nickname and enabled='true'";
        Map<String, Object> map = new HashMap<>();
        map.put("nickname",alias);
        List<Category>list=namedParameterJdbcTemplate.query(sql,map,new CategoryMapper());
        if(list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<Category> listRootCategories() {
        String sql="select * from categories where parent_id is null";
        Map<String, Object> map = new HashMap<>();
        List<Category>list=namedParameterJdbcTemplate.query(sql,map,new CategoryMapper());
        if(list.size()>0){
            return list;
        }
        return null;
    }

    @Override
    public List<Category> selectNestedCategoriesWithParentId() {
        String sql="WITH RECURSIVE CategoryHierarchy AS (" +
                "    SELECT" +
                "        id," +
                "        name," +
                "        parent_id," +
                "        0 AS level" +
                "    FROM" +
                "        categories" +
                "    WHERE" +
                "        parent_id IS NULL" +
                "    UNION ALL" +
                "    SELECT" +
                "        c.id," +
                "        c.name," +
                "        c.parent_id, " +
                "        ch.level + 1 " +
                "    FROM " +
                "        categories c " +
                "            INNER JOIN " +
                "        CategoryHierarchy ch ON c.parent_id = ch.id " +
                ") " +
                " SELECT" +
                "    id," +
                "    name," +
                "    parent_id," +
                "    level" +
                " FROM" +
                "    CategoryHierarchy " +
                " ORDER BY" +
                "    level, id";
        Map<String,Object> map=new HashMap<>();
        List<Category> list=namedParameterJdbcTemplate.query(sql,map,new NestedCategoryMapper());
        if(list.size()>0){
            return list;
        }
        return null;
    }
}
