package com.example.demo01.src.DAO;

import com.example.demo01.src.Pojo.Category;

import java.util.List;

public interface CategoryDAO {


     List<Category> getallCategories();
     List<Category> selectNestedCategoriesWithParentId();

     void saveCategory(Category category);

     Category getcategoryByName(String name);

     Category getCategoriesById(int id);

     void UpdateCategory(Category category);

     String SelectCategoryByName(String name);

     Category findByName(String name);

     void UpdateEnabledStatus(int id, int enabled);

     void deleteCategoryById(int id);

     List<Category> getCategoryByPagination(int Pageno);

     Integer getPageCount();

     Category findByAliasEnabled(String alias);
     List<Category> listRootCategories();
     List<Category> listAllCategoriesOrderedByParentName(String ParentName,int level);
     List<Category>listChildrenCategoreisByParentId(int parentId);


}
