package com.example.demo01.src.DAO;

import com.example.demo01.src.Pojo.Category;
import com.example.demo01.src.Service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@SpringBootTest
class CategoryDAOImplTest {

    @Autowired
    CategoryDAO categoryDAO;
    @Autowired
    CategoryService categoryService;

    @Test
    public void testFindByAliasEnabled(){
        String alias="Computers";
        Category category=categoryDAO.findByAliasEnabled(alias);

       assertNotNull(category);
       assertEquals(category.getNickname(),alias);
       assertEquals(category.getName(),"Computers");
       assertEquals(category.getEnabled(),"true");
        assertEquals(category.getId(),1);
    }
    @Test
    public void testFindcategoryByName(){
        String name="Laptops";
        Category category=categoryDAO.findByName(name);
        assertEquals(name,category.getName());
    }

    @Test
    public void testPageCount(){
        Integer page=categoryDAO.getPageCount();
        page=page/5;
        System.out.println(page);
        List<Integer> list=categoryService.getPageCount();
        list.stream().forEach(s-> System.out.println("number"+s));
    }

    @Test
    public void testHierarchicalCategories() {
        List<Category> childrenCategoryList = categoryDAO.selectNestedCategoriesWithParentId();
        Map<Long, List<Category>> categoryMap = new HashMap<>();

        // Group categories by their parent_id
        for (Category category : childrenCategoryList) {
            long parentId = category.getParentid();
            categoryMap.computeIfAbsent(parentId, k -> new ArrayList<>()).add(category);
        }

        // Display categories in a hierarchical structure
        List<Category> hierarchicalCategoryList = new ArrayList<>();
        displayCategories(categoryMap, 0, 0, hierarchicalCategoryList); // Start with root categories

        // Now, hierarchicalCategoryList contains the sorted hierarchical category list
        System.out.println("hierarchical list"+hierarchicalCategoryList);
    }

    void displayCategories(Map<Long, List<Category>> categoryMap, long parentId, int level, List<Category> hierarchicalCategoryList) {
        if (categoryMap.containsKey(parentId)) {
            for (Category category : categoryMap.get(parentId)) {
                category.setName("-".repeat(level) + category.getName()); // Adjust indentation based on the level);
                hierarchicalCategoryList.add(category); // Add the category to the list
                displayCategories(categoryMap, category.getId(), level + 1, hierarchicalCategoryList); // Recursively add children
            }
        }
    }

    @Test
    public void testUpdateEnabledStatus(){
        categoryDAO.UpdateEnabledStatus(2,0);
    }




}