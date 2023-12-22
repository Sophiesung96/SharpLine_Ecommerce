package com.example.demo01.src.Service;

import com.example.demo01.src.DAO.UsersRoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    UsersRoleDao usersRoleDao;

    @Override
    public void insertUserIdnRoleId(int userid, int roleid) {
        usersRoleDao.insertUserIdnRoleId(userid, roleid);
    }

}
