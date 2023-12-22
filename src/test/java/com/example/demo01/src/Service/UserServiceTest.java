package com.example.demo01.src.Service;

import com.example.demo01.src.DAO.UserDao;
import com.example.demo01.src.Pojo.Users;
import com.example.demo01.src.Pojo.UsersRole;
import com.example.demo01.src.Service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;

    @MockBean
    UserDao userDao;


    @Test
    public void getAlllist() {
        List<Users> list = new ArrayList<>();
        Users user = new Users();
        user.setId(1);
        user.setPassword("$2a$10$F127XwVTUrLNVip/1.yu7OG5XJ5F.95e3gu6XalhiP8LnlrIbVIoG");
        user.setEmail("meowmeow@gmail.com");
        user.setFirst_name("Sophie");
        user.setLast_name("Liung");
        user.setEnabledStatus("false");
        list.add(user);
        Mockito.when(userDao.listAll()).thenReturn(list);
        List<Users> testlist = new ArrayList<>();
        testlist = userService.listAll();
        assertNotNull(testlist);
        for (Users testuser : testlist) {
            assertEquals(testuser.getId(), 1);
            assertEquals(testuser.getPassword(), "$2a$10$F127XwVTUrLNVip/1.yu7OG5XJ5F.95e3gu6XalhiP8LnlrIbVIoG");
            assertEquals(testuser.getFirst_name(), "Sophie");
            assertEquals(testuser.getLast_name(), "Liung");
            assertEquals(testuser.getEnabledStatus(), "false");
        }


    }

    @Test
    public void getUserRole() {
        UsersRole role = new UsersRole();
        role.setName("Admin");
        role.setName("Editor");
        role.setName("Salesperson");
        role.setName("Salesperson");
        role.setName("Shipper");
        role.setName("Assistant");
        List<UsersRole> list = new ArrayList<>();
        list.add(role);
        Mockito.when(userDao.FindUserRole()).thenReturn(list);
        List<UsersRole> testlist = new ArrayList<>();
        testlist = userService.getUserRole();
        for (UsersRole usersRole : testlist) {
            assertNotNull(usersRole);
        }
    }

    @Test
    public void getUserByPagination() {
        List<Users> list = new ArrayList<>();
        list.add(new Users(1, "meowmeow@gmail.com", "false", "Sophie", "Liung", "$2a$10$F127XwVTUrLNVip/1.yu7OG5XJ5F.95e3gu6XalhiP8LnlrIbVIoG"));
        list.add(new Users(2, "nam@codejava.net", "true", "Nam", "Nung", "{noop}ha123"));
        Mockito.when(userDao.getUserByPagination(Mockito.eq(1))).thenReturn(list);
        Users tuser = list.stream().filter(test -> "false".equals(test.getEnabledStatus()) && "Sophie".equals(test.getFirst_name()) && "meowmeow@gmail.com".equals(test.getEmail())).findAny().orElse(null);
        assertEquals(1, tuser.getId());
        assertEquals("false", tuser.getEnabledStatus());
        assertEquals("meowmeow@gmail.com", tuser.getEmail());
        assertEquals("Sophie", tuser.getFirst_name());


    }


}
