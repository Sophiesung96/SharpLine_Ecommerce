package com.example.demo01.src.DAO;

import com.example.demo01.src.Pojo.Brand;
import com.example.demo01.src.Pojo.BrandCategoryName;
import com.example.demo01.src.Pojo.Users;
import com.example.demo01.src.Service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class UsersRoleDaoImpllTest {


    @Autowired
    UserDao userDao;

    @Autowired
    CategoryDAO categoryDAO;

    @Autowired
    CategoryService categoryService;

    @Autowired
    BrandDAO brandDAO;


    @Test
    @Transactional
    public void getUserByUsername() {
        Users user = userDao.getUser("Sophie");
        assertEquals("Sophie", user.getFirst_name());
        assertNotNull(user);
        assertEquals("Liung", user.getLast_name());

        assertNotNull(categoryService.getPageCount());
        List<BrandCategoryName> list = new ArrayList<>();
        BrandCategoryName b=new BrandCategoryName();
        Brand brand = brandDAO.getBrandIdByName("Acer");
        int id = brand.getId();
        assertEquals(1, id);
        Brand brand1 = new Brand();
        brand1.setName("testing");
        brandDAO.editBrandById(brand1);
    }

    @Test
    public void getFILTER() {
        String keyword = "N";
        List<Brand> list = brandDAO.getFilterByKeyword(keyword);
        for (Brand brand : list) {
            System.out.println(brand.getName());
        }
        Brand brand = brandDAO.selectBrandById(2);
        assertEquals(2, brand.getId());
    }


}


