package com.example.demo01.src.DAO;

import com.example.demo01.src.Pojo.Role;
import com.example.demo01.src.Pojo.Users;
import com.example.demo01.src.Pojo.UsersRole;

import java.util.List;

public interface UserDao {
    public void insertUser(Users user);

    public void updateUser(Users user);

    public Users getUser(String username);

    public List<Users> ReadByUid(Integer uid);

    public List<Users> listAll();

    public List<UsersRole> FindUserRole();

    public List<Role> getAllRole();

    public List<Users> getUserIdbyName(String username);

    public List<Users> getUserByEmail(String email);

    public String getPassword(Users user);

    public void DeleteUserById(Integer id);

    public void UpdateEnabledStatus(Integer id, String EnabledStatus);

    public List<Users> getUserByPagination(int Pageno);

    public Integer getCountByUser();

    public List<Users> usingKeywordfindUser(String firstname, String lastname);

    public List<UsersRole> FindUserRoleByUser(Users user);

    public List<Users> getUserbyName(String username);

}
