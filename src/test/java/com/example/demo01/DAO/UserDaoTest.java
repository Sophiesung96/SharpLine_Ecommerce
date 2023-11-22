package com.example.demo01.src.test.java.com.example.springboot_ecommerce.DAO;

import com.example.springboot_ecommerce.Pojo.Category;
import com.example.springboot_ecommerce.Pojo.Users;
import com.example.springboot_ecommerce.Service.BrandService;
import com.example.springboot_ecommerce.Service.CategoryService;
import com.example.springboot_ecommerce.Service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserDaoTest {

    @Autowired
    UserDao userDao;

    @Autowired
    CategoryService categoryService;

    @Autowired
    BrandService brandService;


    @MockBean
    CategoryDAO categoryDAO;
    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder encoder;
    private static final Logger logger = LoggerFactory.getLogger(UserDaoTest.class);

    @Test
    public void TestGetUserByEmail() {
        String email = "meowmeow@gmail.com";
        List<Users> list = new ArrayList<>();
        Users user = new Users();
        user.setEmail("tu6010534@gmail.com");


    }

    @Test
    @Transactional
    public void TestDeleteUserbyId() {
        Users user = new Users();
        user.setId(1);
        userDao.DeleteUserById(user.getId());
        assertNotNull(user);
    }


    @Test
    public void TestSearchUserByFirstname() {
        List<Users> list = new ArrayList<>();
        String username = "so";

        String test = "123";
        String password = encoder.encode(test);
        boolean y = encoder.matches("123", password);
        System.out.println(password);

    }

    @Test
    public void TestUnique() {
        Integer id = 0;
        String name = "Computers";
        String nname = "Computers";
        Category category = new Category(1, name, nname);
        Mockito.when(categoryDAO.findByName(name)).thenReturn(category);
        assertEquals(1, category.getId());
    }

}




