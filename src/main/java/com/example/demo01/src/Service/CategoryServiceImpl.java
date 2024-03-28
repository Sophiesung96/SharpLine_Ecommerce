package com.example.demo01.src.Service;

import com.example.demo01.src.DAO.CategoryDAO;
import com.example.demo01.src.Pojo.PageNumber;
import com.example.demo01.src.Pojo.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {


    @Autowired
    CategoryDAO categoryDAO;







    public List<Category> getallList() {
        List<Category> list = new ArrayList<>();
        list = categoryDAO.listRootCategories();
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
    public void UpdateEnabledStatus(int id, int enabled) {
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
        number = number / 5;
        if (number % 5 != 0) {
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

    @Override
    public List<Category> listRootCategories() {
        List<Category> list=categoryDAO.listRootCategories();
        return list;
    }

    @Override
    public List<Category> GetHierarchicalCategories() {
        List<Category> childrenCategoryList = categoryDAO.selectNestedCategoriesWithParentId();
        Map<Integer, List<Category>> categoryMap = new HashMap<>();

        // Group categories by their parent_id
        for (Category category : childrenCategoryList) {
            Integer parentId = category.getParentid();
            //If there's no list associated with the parentId in the categoryMap,
            // a new ArrayList<> is then created and associated with that parentId.
            categoryMap.computeIfAbsent(parentId, k -> new ArrayList<>()).add(category);
        }

        // Display categories in a hierarchical structure
        List<Category> hierarchicalCategoryList = new ArrayList<>();
        displayCategories(categoryMap, 0, 0, hierarchicalCategoryList); // Start with root categories

        // Now, hierarchicalCategoryList contains the sorted hierarchical category list
        System.out.println("hierarchical list"+hierarchicalCategoryList);
        return hierarchicalCategoryList;
    }

    private void displayCategories(Map<Integer, List<Category>> categoryMap, Integer parentId, int level, List<Category> hierarchicalCategoryList) {
        if (categoryMap.containsKey(parentId)) {
            for (Category category : categoryMap.get(parentId)) {
                String indentation = " ".repeat(level * 2); // Adjust the number of spaces based on the level
                String dashes = "-".repeat(level); // Adjust the number of dashes based on the level
                System.out.println("New Sorted Category List"+indentation + dashes + " " + category.getName());
                category.setName(indentation + dashes + " " + category.getName());
                hierarchicalCategoryList.add(category); // Add the category to the list
                displayCategories(categoryMap, category.getId(), level + 1, hierarchicalCategoryList); // Recursively add children
            }
        }
    }



    @Override
    public List<Category> listAllCategoriesOrderedByParentName(String ParentName,int level) {
        List<Category> list=categoryDAO.listAllCategoriesOrderedByParentName(ParentName, level);
        return list;
    }

    @Override
    public List<Category> listChildrenCategoreisByParentId(int parentId) {
        List<Category>list=categoryDAO.listChildrenCategoreisByParentId(parentId);
        return list;
    }
}


