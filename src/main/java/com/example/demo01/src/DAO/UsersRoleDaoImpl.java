package com.example.demo01.src.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
@Repository
public class UsersRoleDaoImpl implements UsersRoleDao{

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void insertUserIdnRoleId(int userid, int roleid) {

        String sql="insert into users_roles(user_id,role_id) values(:userid, roleid)";
        Map<String,Object> map=new HashMap<>();
        map.put("userid",userid);
        map.put("roleid",roleid);
        namedParameterJdbcTemplate.update(sql,map);
    }
}
