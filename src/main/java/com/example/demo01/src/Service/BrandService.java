package com.example.demo01.src.Service;

import com.example.springboot_ecommerce.Pojo.Brand;
import com.example.springboot_ecommerce.Pojo.BrandCategoryName;

import java.util.List;

public interface BrandService {
    List<Brand> getallBrand();

    List<BrandCategoryName> getCategoryName();

    public void saveBrand(Brand brand);

    public Brand getBrandIdByName(String name);

    public void createBrandCategory(int brandid, int categoryid);

    void deleteBrandById(int id);

    void editBrandById(Brand brand);

    Brand selectBrandById(int id);

    boolean checkNameUnique(String name);

    List<Brand> getFilterByKeyword(String keyword);

    List<Brand> getBrandByPagination(int pageno);

    public List<Integer> getPageCount();

    List<BrandCategoryName> getCategoryById(int id);
}

