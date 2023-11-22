package com.example.demo01.src.DAO;

import com.example.demo01.src.Pojo.Category;

import java.util.List;

public interface CategoryDAO {

    public List<Category> getallCategories();

    public void saveCategory(Category category);

    public Category getcategoryByName(String name);

    public Category getCategoriesById(int id);

    public void UpdateCategory(Category category);

    public String SelectCategoryByName(String name);

    public Category findByName(String name);

    public void UpdateEnabledStatus(int id, String enabled);

    public void deleteCategoryById(int id);

    public List<Category> getCategoryByPagination(int Pageno);

    public Integer getPageCount();

    public Category findByAliasEnabled(String alias);


}
