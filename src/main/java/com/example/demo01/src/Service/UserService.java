package com.example.demo01.src.Service;

import com.example.demo01.src.Pojo.Role;
import com.example.demo01.src.Pojo.Users;
import com.example.demo01.src.Pojo.UsersRole;

import java.util.List;

public interface UserService {

    public List<Users> listAll();

    public List<UsersRole> getUserRole();

    public void CreateUser(Users user);

    public List<Role> getAllRole();

    public Users getUserIdByName(String username);

    public boolean isEmailUnique(String email);

    public Users ReadByUid(Integer id);

    public void UpdateUser(Users user);

    public void DeleteUserById(Integer id);

    public void UpdateEnabledStatus(Integer id, String enablestatus);

    public List<Users> getUserByPagination(int pageno);

    public List<Integer> getCountByUser();

    public List<Users> usingKeywordfindUser(String firstname, String lastname);

    List<Users> getEmailByUser(String email);

    public List<UsersRole> FindUserRoleByUser(Users user);

    public List<Users> getUserbyName(String username);
}
