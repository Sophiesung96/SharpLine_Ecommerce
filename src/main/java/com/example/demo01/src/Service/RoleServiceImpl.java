package com.example.demo01.src.Service;

import com.example.demo01.src.DAO.RoleDao;
import com.example.demo01.src.Pojo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleDao roleDao;

}
