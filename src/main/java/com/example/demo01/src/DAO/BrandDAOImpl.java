package com.example.demo01.src.DAO;

import com.example.demo01.src.Mapper.BrandCategoryNameMapper;
import com.example.demo01.src.Mapper.BrandMapper;
import com.example.demo01.src.Mapper.PageNumberMapper;
import com.example.demo01.src.Pojo.Brand;
import com.example.demo01.src.Pojo.BrandCategoryName;
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
public class BrandDAOImpl implements BrandDAO {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Brand> getallBrand() {
        String sql = "select * from Brands order by name asc";
        List<Brand> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        list = namedParameterJdbcTemplate.query(sql, map, new BrandMapper());
        if (list.size() > 0) {
            return list;


        }
        return null;
    }

    @Override
    public void saveBrand(Brand brand) {
        String sql = "insert into Brands(name,logo) values(:name,:logo)";
        Map<String, Object> map = new HashMap<>();
        map.put("name", brand.getName());
        map.put("logo", brand.getLogo());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

    }

    @Override
    public List<BrandCategoryName> getCategoryName() {
        String sql = "select c.id as cid,c.name as cname from Brands b inner join  Brands_categories  br on b.id=br.brand_id inner join categories c on br.category_id = c.id";
        Map<String, Object> map = new HashMap<>();
        List<BrandCategoryName> list = new ArrayList<>();
        list = namedParameterJdbcTemplate.query(sql, map, new BrandCategoryNameMapper());
        if (list.size() > 0) {
            return list;

        }
        return null;
    }


    @Override
    public Brand getBrandIdByName(String name) {
        String sql = "select * from Brands where name=:name";
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        List<Brand> list = new ArrayList<>();
        list = namedParameterJdbcTemplate.query(sql, map, new BrandMapper());
        Brand brand = list.get(0);
        return brand;
    }

    @Override
    public void createBrandCategory(int brandid, int categoryid) {
        String sql = "insert into Brands_categories(brand_id,category_id) value(:brandid,:categoryid)";
        Map<String, Object> map = new HashMap<>();
        map.put("brandid", brandid);
        map.put("categoryid", categoryid);
        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void deleteBrandById(int id) {
        String sql = "delete from Brands where id=:id";
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public Brand selectBrandById(int id) {
        String sql = "select * from Brands where id=:id";
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        List<Brand> list = new ArrayList<>();
        list = namedParameterJdbcTemplate.query(sql, map, new BrandMapper());
        Brand brand = list.get(0);
        return brand;
    }

    @Override
    public void editBrandById(Brand brand) {
        String sql = "update Brands set name=:name where id=:id";
        Map<String, Object> map = new HashMap<>();
        map.put("name", brand.getName());
        map.put("id", brand.getId());
        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public List<Brand> checkNameUnique(String name) {
        String sql = "select * from Brands where name=:name";
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        List<Brand> list = new ArrayList<>();
        list = namedParameterJdbcTemplate.query(sql, map, new BrandMapper());
        return list;
    }

    @Override
    public List<Brand> getBrandByPagination(int pageno) {
        String sql = "select * from Brands limit :pageno,10";
        Map<String, Object> map = new HashMap<>();
        map.put("pageno", (pageno - 1) * 10);
        List<Brand> list = new ArrayList<>();
        list = namedParameterJdbcTemplate.query(sql, map, new BrandMapper());
        if (list.size() > 0) {
            return list;
        }
        return null;
    }

    @Override
    public List<Brand> getFilterByKeyword(String keyword) {
        String sql = "select * from Brands where name like :keyword";
        Map<String, Object> map = new HashMap<>();
        map.put("keyword", "%" + keyword + "%");
        List<Brand> list = new ArrayList<>();
        list = namedParameterJdbcTemplate.query(sql, map, new BrandMapper());
        if (list.size() > 0) {
            return list;
        }
        return null;
    }

    @Override
    public Integer getBrandCount() {
        String sql = "select count(*) as total from Brands";
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
    public List<BrandCategoryName> getCategoryById(int id) {
        String sql = "select c.id as cid,c.name as cname from Brands b inner join  Brands_categories  br on b.id=br.brand_id inner join categories c on br.category_id = c.id where b.id=:bid";
        Map<String, Object> map = new HashMap<>();
        map.put("bid", id);
        List<BrandCategoryName> list = new ArrayList<>();
        list = namedParameterJdbcTemplate.query(sql, map, new BrandCategoryNameMapper());
        if (list.size() > 0) {
            return list;
        }
        return null;
    }
}
