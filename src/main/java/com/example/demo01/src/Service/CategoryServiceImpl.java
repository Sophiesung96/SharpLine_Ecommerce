package com.example.demo01.src.Service;

import com.example.demo01.src.DAO.CategoryDAO;
import com.example.demo01.src.Pojo.PageNumber;
import com.example.demo01.src.Pojo.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {


    @Autowired
    CategoryDAO categoryDAO;

    public List<Category> getallList() {
        List<Category> list = new ArrayList<>();
        list = categoryDAO.getallCategories();
        return list;
    }

    @Override
    public Category getcategoryByName(String name) {
        Category category = new Category();
        category = categoryDAO.getcategoryByName(name);
        return category;
    }

    @Override
    public void saveCategory(Category category) {
        categoryDAO.saveCategory(category);
    }

    @Override
    public Category getCategoriesById(int id) {
        Category category = categoryDAO.getCategoriesById(id);
        return category;
    }

    @Override
    public void UpdateCategory(Category category) {
        categoryDAO.UpdateCategory(category);
    }

    @Override
    public String GetImage(String name) {
        String newimage = categoryDAO.SelectCategoryByName(name);

        return newimage;
    }

    @Override
    public boolean checkUnique(String name) {
        Category categoryByName = categoryDAO.findByName(name);

        return categoryByName == null;


    }

    @Override
    public void UpdateEnabledStatus(int id, String enabled) {
        categoryDAO.UpdateEnabledStatus(id, enabled);
    }

    @Override
    public void deleteCategoryById(int id) {
        categoryDAO.deleteCategoryById(id);
    }

    @Override
    public List<Category> getCategoryByPagination(int Pageno) {
        List<Category> list = new ArrayList<>();
        list = categoryDAO.getCategoryByPagination(Pageno);
        return list;
    }

    @Override
    public List<Integer> getPageCount() {
        List<PageNumber> list = new ArrayList<>();
        int number = 0;
        number = categoryDAO.getPageCount();
        number = number / 4;
        if (number % 4 != 0) {
            number += 1;
        }
        List<Integer> numberlist = new ArrayList<>();
        for (int i = 1; i <= number; i++) {
            numberlist.add(i);
        }
        return numberlist;
    }

    @Override
    public Category findByAliasEnabled(String alias) {
        Category category=categoryDAO.findByAliasEnabled(alias);
        return category;
    }
}


