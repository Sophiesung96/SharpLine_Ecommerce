package com.example.demo01.src.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class RoleDaoImpl implements RoleDao {
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Override
    public void insertUserRole(int userid, int roleid) {
        String sql = "insert into Users_role(user_id, role_id) vale(:userid, :roleid)";
        Map<String, Object> map = new HashMap<>();
        map.put("userid", userid);
        map.put("roleid", roleid);
        namedParameterJdbcTemplate.update(sql, map);


    }
}
