package com.example.demo01.src.DAO;

import com.example.demo01.src.Mapper.*;
import com.example.demo01.src.Pojo.*;
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
public class ProductDAOImpl implements ProductDAO {
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Product> listAll() {
        String sql = "select * from products";
        Map<String, Object> map = new HashMap<>();
        List<Product> list = new ArrayList<>();
        list = namedParameterJdbcTemplate.query(sql, map, new ProductMapper());
        if (list.size() > 0) {
            return list;
        }
        return null;
    }

    @Override
    public List<ProductCBName> selectCategoyrnBrandByName() {
        String sql = "select b.id as bid ,c.id as cid ,c.name as category_name, b.name  as brand_name from products p inner join categories c on p.category_id = c.id inner join Brands b\n" +
                " on p.brand_id=b.id";
        List<ProductCBName> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        list = namedParameterJdbcTemplate.query(sql, map, new ProductCBNameMapper());
        return list;
    }

    @Override
    public List<Product> listAllOrderByName() {
        String sql = "select *from Brands order by name asc";
        Map<String, Object> map = new HashMap<>();
        List<Product> list = new ArrayList<>();
        list = namedParameterJdbcTemplate.query(sql, map, new ProductMapper());
        if (list.size() > 0) {
            return list;
        }
        return null;
    }

    @Override
    public void saveProduct(Product product) {
        String sql = "insert into products (name,alias,short_description,full_context,main_image,created_Time,updated_Time,enabled,in_stock," +
                "list_price,discount_percent,brand_id,category_id,length,width,height,weight,average_rating,review_Count,cost) values(:name,:alias,:short_description,:full_context,:main_image,:created_Time,:updated_Time,:enabled,:in_stock," +
                ":list_price,:discount_percent,:brand_id,:category_id,:length,:width,:height,:weight,:average_rating,:review_Count,:cost)";
        Map<String, Object> map = new HashMap<>();
        map.put("name", product.getName());
        map.put("alias", product.getAlias());
        map.put("short_description", product.getShortDescription());
        map.put("full_context", product.getFullContent());
        map.put("main_image", product.getMainimage());
        map.put("created_Time", product.getCreatedTime());
        map.put("updated_Time", product.getUpdatedTime());
        map.put("enabled", product.getEnabled());
        map.put("in_stock", product.getInStock());
        map.put("list_price", product.getPrice());
        map.put("discount_percent", product.getDiscountPercent());
        map.put("brand_id", product.getBrandId());
        map.put("category_id", product.getCategoryId());
        map.put("length", product.getLength());
        map.put("width", product.getWidth());
        map.put("height", product.getHeight());
        map.put("weight", product.getWeight());
        map.put("average_rating", product.getAverageRating());
        map.put("review_Count", product.getReview());
        map.put("cost", product.getCost());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);


    }

    @Override
    public List<Product> selectProductByName(Product product) {
        String sql = "select * from products where name=:name";
        List<Product> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("name", product.getName());
        list = namedParameterJdbcTemplate.query(sql, map, new ProductMapper());
        Product P = new Product();
        if (list.size() > 0) {
            return list;
        }
        return null;
    }


    @Override
    public void UpdateEnabledStatus(int id, int enabled) {
        String sql = "update products set enabled=:enabled where id=:id";
        Map<String, Object> map = new HashMap<>();
        map.put("enabled", enabled);
        map.put("id", id);
        namedParameterJdbcTemplate.update(sql, map);

    }

    @Override
    public void deleteProductById(int id) {
        String sql="delete from products where id=:id";
        Map<String, Object> map = new HashMap<>();
        map.put("id",id);
        namedParameterJdbcTemplate.update(sql,map);
    }


    @Override
    public void saveProductDetails(ProductDetail detail,int pid) {
        String sql="insert into product_details(name,value,product_id) values(:name,:value,:product_id)";
        Map<String, Object> map = new HashMap<>();
        map.put("name",detail.getName());
        map.put("value",detail.getValue());
        map.put("product_id",pid);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);
    }

    @Override
    public Product editProductById(int id) {
        String sql="select * from products where id=:id";
        Map<String, Object> map = new HashMap<>();
        map.put("id",id);
        List<Product> list = new ArrayList<>();
        list=namedParameterJdbcTemplate.query(sql,map,new ProductMapper());
        if(list.size()>0){
            return  list.get(0);

        }
        return null;
    }

    @Override
    public ProductCBName selectCategoyrnBrandByProductId(int id) {
        String sql="select  B.name as brand_name,B.id as bid,c.id as cid, c.name as category_name from products inner join Brands B on products.brand_id = B.id inner join categories c on products.category_id = c.id where products.id=:id";
        Map<String,Object> map=new HashMap<>();
        map.put("id",id);
        List<ProductCBName>list=new ArrayList<>();
        list=namedParameterJdbcTemplate.query(sql,map,new ProductCBNameMapper());
        ProductCBName productCBName=new ProductCBName();
        productCBName=list.get(0);
        return productCBName;
    }

    @Override
    public void saveExtraImagesofProduct(String name, Product product) {
        String sql="insert into product_image (name,product_id) values(:name,:product_id)";
        Map<String,Object> map=new HashMap<>();
        map.put("name",name);
        map.put("product_id",product.getId());
        KeyHolder keyHolder=new GeneratedKeyHolder();
       namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);

    }


    @Override
    public List<ProductImage> selectExtraByProductId(int id) {
        String sql="select * from product_images where product_id=:pid";
        Map<String,Object> map=new HashMap<>();
        map.put("pid",id);
        List<ProductImage>list=new ArrayList<>();
        list=namedParameterJdbcTemplate.query(sql,map,new ProductImageMapper());
        ProductImage image=new ProductImage();
        if(list.size()>0){
            return list;
        }
        else{
            List<ProductImage> emptylist=new ArrayList<>();
            emptylist.add(new ProductImage());
            return emptylist;
        }


    }

    @Override
    public List<ProductDetail> selectProductDetailsById(int id) {
        String sql="select * from product_details where product_id=:id";
        Map<String,Object> map=new HashMap<>();
        map.put("id",id);
        List<ProductDetail>list=new ArrayList<>();
        list=namedParameterJdbcTemplate.query(sql,map,new ProductDetailMapper());
        if(list.size()>0){
            return list;
        }
        return null;
    }

    @Override
    public void saveEditedProductById(Product product) {
        String sql="update products set name=:name,alias=:alias,short_description=:short_description,full_description=:full_context," +
                "main_Image=:main_image,updated_Time=:updated_Time,enabled=:enabled,in_stock=:in_stock, price=:list_price,discount_percent=:discount_percent," +
                "brand_id=:brand_id,category_id=:category_id,length=:length,width=:width,height=:height,weight=:weight,average_rating=:average_rating,review_Count=:review_Count," +
                "cost=:cost where id=:id";
        Map<String,Object>map=new HashMap<>();
        map.put("name", product.getName());
        map.put("alias", product.getAlias());
        map.put("short_description", product.getShortDescription());
        map.put("full_context", product.getFullContent());
        map.put("main_image", product.getMainimage());
        map.put("created_Time", product.getCreatedTime());
        map.put("updated_Time", product.getUpdatedTime());
        map.put("enabled", product.getEnabled());
        map.put("in_stock", product.getInStock());
        map.put("list_price", product.getPrice());
        map.put("discount_percent", product.getDiscountPercent());
        map.put("brand_id", product.getBrandId());
        map.put("category_id", product.getCategoryId());
        map.put("length", product.getLength());
        map.put("width", product.getWidth());
        map.put("height", product.getHeight());
        map.put("weight", product.getWeight());
        map.put("average_rating", product.getAverageRating());
        map.put("review_Count", product.getReview());
        map.put("cost", product.getCost());
        map.put("id",product.getId());
        namedParameterJdbcTemplate.update(sql,map);

    }

    @Override
    public void UpdateProductImage(ProductImage image,Product product) {
        String sql="update product_image set name=:name where product_id=:id";
        Map<String,Object>map=new HashMap<>();
        map.put("product_id",product.getId());
        KeyHolder keyHolder=new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);

    }

    @Override
    public void UpdateProductDetails(ProductDetail productDetail, Product product) {
        String sql="update product_details set name=:name, value=:value where product_id=:product_id and id=:id";
        Map<String,Object>map=new HashMap<>();
        map.put("product_id",product.getId());
        map.put("id",productDetail.getId());
        map.put("name",productDetail.getName());
        map.put("value",productDetail.getValue());
        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
      public void DeleteProductDetailsByProductId(int productId,int id) {
        String sql="delete from product_details where  id=:id";
        Map<String,Object>map=new HashMap<>();
        map.put("id",id);
        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public List<Product> selectProductByPagination(int pageno) {
        String sql="select * from products limit :pageno,10";
        Map<String,Object>map=new HashMap<>();
        map.put("pageno",(pageno-1)*10);
        List<Product>list=new ArrayList<>();
        list=namedParameterJdbcTemplate.query(sql,map,new ProductMapper());
        if(list.size()>0){
            return list;
        }
        return null;
    }

    @Override
    public List<PageNumber> getPageCount() {
        String sql="select count(*)as total from products";
        List<PageNumber>list=new ArrayList<>();
        Map<String,Object>map=new HashMap<>();
        list=namedParameterJdbcTemplate.query(sql,map,new PageNumberMapper());
        if(list.size()>0){
            return  list;
        }
        return null;
    }

    @Override
    public List<PageNumber> getPageCountForCategoriesWithParentId(int parentId) {
        String sql="select count(*) as total from categories" +
                " inner join products on categories.id = products.category_id where categories.parent_id=:parentId";
        List<PageNumber>list=new ArrayList<>();
        Map<String,Object>map=new HashMap<>();
        map.put("parentId",parentId);
        list=namedParameterJdbcTemplate.query(sql,map,new PageNumberMapper());
        if(list.size()>0){
            return  list;
        }
        return null;
    }

    @Override
    public List<Product> getProductByCategoryId(int categoryId,int pageno) {
        String sql="select * from products p  where p.enabled=1 and p.category_id=:cid order by p.name asc limit :pageno,10 ";
        Map<String,Object>map=new HashMap<>();
        map.put("cid",categoryId);
        map.put("pageno",(pageno-1)*10);
        List<Product> list=namedParameterJdbcTemplate.query(sql,map,new ProductMapper());
        if(list.size()>0){
            return list;

        }        return null;
    }


    @Override
    public Product findByNickName(String alias) {
        Product product = null;
        String sql = "SELECT * FROM products WHERE alias = :alias"; // Modify SQL query
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("alias", alias); // Pass alias to SQL query
        List<Product> list = namedParameterJdbcTemplate.query(sql, paramMap, new ProductMapper());
        if (!list.isEmpty()) {
            product = list.get(0);
        }
        return product;
    }


    @Override
    public List<Product> SearchByKeyword(int pageno, String search) {
        String sql= "SELECT * FROM products WHERE MATCH(name,short_description,full_description) AGAINST(:search) limit :pageno,10";
        Map<String,Object>map=new HashMap<>();
        map.put("search",search);
        map.put("pageno",(pageno-1)*10);
        List<Product> list=namedParameterJdbcTemplate.query(sql,map,new ProductMapper());
        if(list.size()>0){
            return list;
        }
        return null;
    }

    @Override
    public List<PageNumber> getFilteredPageCount(String search) {
        String sql="select count(*)as total from products  WHERE MATCH(name,short_description,full_description) AGAINST(:search)";
        List<PageNumber>list=new ArrayList<>();
        Map<String,Object>map=new HashMap<>();
        map.put("search",search);
        list=namedParameterJdbcTemplate.query(sql,map,new PageNumberMapper());
        if(list.size()>0){
            return  list;
        }
        return null;
    }

    @Override
    public Product findById(int id) {
        String sql="select * from products where id=:id";
        Map<String,Object>map=new HashMap<>();
        map.put("id",id);
       List<Product>list= namedParameterJdbcTemplate.query(sql,map,new ProductMapper());
        if(list.size()>0){
            return list.get(0);

        }
        return null;
    }

    @Override
    public List<Product> ProductSearchByKeywordforOrder(int pageno, String search) {
        String sql= "SELECT * FROM products WHERE MATCH(name,short_description,full_context) AGAINST(:search) limit :pageno,5";
        Map<String,Object>map=new HashMap<>();
        map.put("search",search);
        map.put("pageno",(pageno-1)*5);
        List<Product> list=namedParameterJdbcTemplate.query(sql,map,new ProductMapper());
        if(list.size()>0){
            return list;
        }
        return null;
    }

    @Override
    public void UpdateReviewCountandAverageRating(int productId) {
      String sql="update  products p set average_rating =(select avg(rating) from reviews r where r.product_Id=:productId)," +
              "   review_Count=(select count(r.id) from reviews r where r.product_Id=:productId) where p.id=:productId";
      Map<String,Object> map=new HashMap<>();
      map.put("productId",productId);
      namedParameterJdbcTemplate.update(sql,map);
    }
}




