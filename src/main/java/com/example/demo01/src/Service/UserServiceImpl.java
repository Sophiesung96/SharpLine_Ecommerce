package com.example.demo01.src.Service;

import com.example.demo01.src.DAO.UserDao;
import com.example.demo01.src.Pojo.Role;
import com.example.demo01.src.Pojo.Users;
import com.example.demo01.src.Pojo.UsersRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserDao userDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public List<Users> listAll() {
        List<Users> list = new ArrayList<>();
        list = userDao.listAll();
        return list;
    }

    @Override
    public List<UsersRole> getUserRole() {
        List<UsersRole> list = new ArrayList<>();
        list = userDao.FindUserRole();

        return list;
    }

    @Override
    public void CreateUser(Users user) {
        Users users = userDao.getUser(user.getFirst_name());
        if (users != null) {
            logger.error("Already has this user");
            throw new IllegalArgumentException("Already has this user");

        } else {
            encodePassWord(user);
            userDao.insertUser(user);
        }
    }

    @Override
    public List<Role> getAllRole() {
        List<Role> list = new ArrayList<>();
        list = userDao.getAllRole();
        return list;
    }

    @Override
    public Users getUserIdByName(String username) {
        Users user = new Users();
        user = userDao.getUser(username);
        return user;
    }

    //encrypt user password
    public void encodePassWord(Users user) {

        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
    }

    @Override
    public boolean isEmailUnique(String email) {
        List<Users> list = new ArrayList<>();
        list = userDao.getUserByEmail(email);

        /* If the email has already existed in the DB,
         * then user would not be null- this indicates that
         * this email is not unique, vice versa.
         */
        return list == null;
    }

    @Override
    public Users ReadByUid(Integer id) {
        try {
            List<Users> list = userDao.ReadByUid(id);
            Users user = list.get(0);
            return user;
        } catch (Exception e) {
            throw new UsernameNotFoundException("Could not find any user");
        }

    }

    @Override
    public void UpdateUser(Users user) {

        if (user.getPassword() != null) {
            encodePassWord(user);
            userDao.updateUser(user);
        } else {
            String password = userDao.getPassword(user);
            user.setPassword(password);
            userDao.updateUser(user);
        }

    }

    @Override
    public void DeleteUserById(Integer id) {
        userDao.DeleteUserById(id);
    }


    @Override
    public void UpdateEnabledStatus(Integer id, int enabled) {
        userDao.UpdateEnabledStatus(id, enabled);
    }

    @Override
    public List<Users> getUserByPagination(int pageno) {
        return userDao.getUserByPagination(pageno);
    }

    @Override
    public List<Integer> getCountByUser() {
        int number = 0;
        number = userDao.getCountByUser();
        int totalPage = number % 10;
        if (totalPage != 0) {
            totalPage += 1;
        }
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= totalPage; i++) {
            list.add(i);
        }
        return list;
    }

    @Override
    public List<Users> usingKeywordfindUser(String firstname, String lastname) {
        List<Users> list = new ArrayList<>();
        list = userDao.usingKeywordfindUser(firstname, lastname);
        if (list.size() > 0) {
            return list;
        } else {
            return null;
        }

    }

    @Override
    public List<Users> getEmailByUser(String email) {
        List<Users> list = new ArrayList<>();
        list = userDao.getUserByEmail(email);
        if (list.size() > 0) {
            return list;
        }
        return null;
    }

    public List<UsersRole> FindUserRoleByUser(Users user) {
        return userDao.FindUserRoleByUser(user);
    }


    @Override
    public List<Users> getUserbyName(String username) {
        List<Users> list = new ArrayList<>();
        list = userDao.getUserbyName(username);
        return list;
    }


}
