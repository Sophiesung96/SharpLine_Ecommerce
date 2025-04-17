package com.example.demo01.src.Service;

import com.example.demo01.src.Pojo.Category;
import com.example.demo01.src.Pojo.PageNumber;

import java.util.List;

public interface CategoryService {

    public List<Category> getallList();
    public Category getcategoryByName(String name);
    public void saveCategory(Category category);
    public Category getCategoriesById(int id);

    public void UpdateCategory(Category category);

    public String GetImage(String name);

    public boolean checkUnique(String name);

    public void UpdateEnabledStatus(int id,int enabled);

    public void deleteCategoryById(int id);

    public List<Category> getCategoryByPagination(int Pageno);
    List<Category> listRootCategories();
    public List<Integer> getPageCount();
    public Category findByAliasEnabled(String alias);
    List<Category> GetHierarchicalCategories();
    public List<Category> listAllCategoriesOrderedByParentName(String ParentName,int level);
    List<Category>listChildrenCategoreisByParentId(int parentId);
}
