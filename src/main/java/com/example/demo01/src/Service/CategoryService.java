package com.example.demo01.src.Service;

import com.example.demo01.src.Pojo.Category;
import com.example.demo01.src.Pojo.PageNumber;

import java.util.List;

public interface CategoryService {

    public List<Category> getallList();
    public Category getcategoryByName(String name);
    List<Category> listHierarchicalCategories(List<Category> list);
    public void saveCategory(Category category);

    public Category getCategoriesById(int id);

    public void UpdateCategory(Category category);

    public String GetImage(String name);

    public boolean checkUnique(String name);

    public void UpdateEnabledStatus(int id, String enabled);

    public void deleteCategoryById(int id);

    public List<Category> getCategoryByPagination(int Pageno);

    public List<Integer> getPageCount();
    public Category findByAliasEnabled(String alias);
}
