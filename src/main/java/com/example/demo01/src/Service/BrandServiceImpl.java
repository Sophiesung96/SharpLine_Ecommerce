package com.example.demo01.src.Service;

import com.example.springboot_ecommerce.DAO.BrandDAO;
import com.example.springboot_ecommerce.Pojo.Brand;
import com.example.springboot_ecommerce.Pojo.BrandCategoryName;
import com.example.springboot_ecommerce.Pojo.PageNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    BrandDAO brandDAO;

    @Override
    public List<Brand> getallBrand() {
        List<Brand> list = new ArrayList<>();
        list = brandDAO.getallBrand();
        return list;
    }

    @Override
    public List<BrandCategoryName> getCategoryName() {
        List<BrandCategoryName> list = new ArrayList<>();
        list = brandDAO.getCategoryName();
        return list;
    }

    @Override
    public void saveBrand(Brand brand) {
        brandDAO.saveBrand(brand);
    }

    @Override
    public Brand getBrandIdByName(String name) {
        Brand brand = brandDAO.getBrandIdByName(name);
        return brand;
    }

    @Override
    public void createBrandCategory(int brandid, int categoryid) {
        brandDAO.createBrandCategory(brandid, categoryid);
    }

    @Override
    public void deleteBrandById(int id) {
        brandDAO.deleteBrandById(id);
    }

    @Override
    public void editBrandById(Brand brand) {
        brandDAO.editBrandById(brand);
    }

    @Override
    public Brand selectBrandById(int id) {
        Brand brand = brandDAO.selectBrandById(id);

        return brand;
    }

    @Override
    public boolean checkNameUnique(String name) {
        List<Brand> list = brandDAO.checkNameUnique(name);
        return list == null;
    }

    @Override
    public List<Brand> getFilterByKeyword(String keyword) {
        List<Brand> list = brandDAO.getFilterByKeyword(keyword);
        return list;
    }

    @Override
    public List<Brand> getBrandByPagination(int pageno) {
        List<Brand> list = brandDAO.getBrandByPagination(pageno);
        return list;
    }

    @Override
    public List<Integer> getPageCount() {
        List<PageNumber> list = new ArrayList<>();
        int number = 0;
        number = brandDAO.getBrandCount();
        number = number / 10;
        if (number % 10 != 0) {
            number += 1;
        }
        List<Integer> numberlist = new ArrayList<>();
        for (int i = 1; i <= number; i++) {
            numberlist.add(i);
        }
        return numberlist;
    }

    @Override
    public List<BrandCategoryName> getCategoryById(int id) {
        List<BrandCategoryName> list = new ArrayList<>();
        list = brandDAO.getCategoryById(id);
        return list;
    }
}
