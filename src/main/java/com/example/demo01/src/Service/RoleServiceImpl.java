package com.example.demo01.src.Service;

import com.example.springboot_ecommerce.DAO.RoleDao;
import com.example.springboot_ecommerce.Pojo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleDao roleDao;

}
