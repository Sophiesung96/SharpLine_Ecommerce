package com.example.demo01.src.DAO;

import com.example.demo01.src.Pojo.Brand;
import com.example.demo01.src.Pojo.BrandCategoryName;

import java.util.List;

public interface BrandDAO {

    public List<Brand> getallBrand();

    public void saveBrand(Brand brand);

    public List<BrandCategoryName> getCategoryName();

    public Brand getBrandIdByName(String name);

    void createBrandCategory(int brandid, int categoryid);

    void deleteBrandById(int id);

    void editBrandById(Brand brand);

    Brand selectBrandById(int id);

    List<Brand> checkNameUnique(String name);

    List<Brand> getBrandByPagination(int pageno);

    List<Brand> getFilterByKeyword(String keyword);

    Integer getBrandCount();

    public List<BrandCategoryName> getCategoryById(int id);


}
