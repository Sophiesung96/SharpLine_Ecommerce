package com.example.demo01.src.DAO;

import com.example.demo01.src.Mapper.PageNumberMapper;
import com.example.demo01.src.Mapper.RoleMapper;
import com.example.demo01.src.Mapper.UserMapper;
import com.example.demo01.src.Mapper.UsersRoleMapper;
import com.example.demo01.src.Pojo.PageNumber;
import com.example.demo01.src.Pojo.Role;
import com.example.demo01.src.Pojo.Users;
import com.example.demo01.src.Pojo.UsersRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void insertUser(Users user) {
        String sql = "INSERT INTO users" +
                "(Email,enable,first_name,last_name,password,photo)" +
                " value " +
                "( :Email, :enabledStatus, :first_name, :last_name, :password, :photo)";
        Map<String, Object> map = new HashMap<>();
        map.put("Email", user.getEmail());
        map.put("enabledStatus", user.getEnabled());
        map.put("first_name", user.getFirst_name());
        map.put("last_name", user.getLast_name());
        map.put("password", user.getPassword());
        map.put("photo", user.getPhoto());
        List<Users> list = new ArrayList<>();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
    }

    @Override
    public void updateUser(Users user) {
        String sql = "update users set email=:email,first_name=:first_name,last_name=:last_name,password=:password,photo=:photo where id=:id";
        Map<String, Object> map = new HashMap<>();
        map.put("email", user.getEmail());
        map.put("enabledStatus", user.getFirst_name());
        map.put("first_name", user.getFirst_name());
        map.put("last_name", user.getLast_name());
        map.put("password", user.getPassword());
        map.put("photo", user.getPhoto());
        map.put("id", user.getId());

        namedParameterJdbcTemplate.update(sql, map);
    }

    public String getPassword(Users user) {
        String sql = "select password from users where id=:id";
        Map<String, Object> map = new HashMap<>();
        map.put("id", user.getId());
        List<Users> list = new ArrayList<>();
        list = namedParameterJdbcTemplate.query(sql, map, new UserMapper());
        Users newuser = list.get(0);
        String password = newuser.getPassword();
        return password;
    }

    @Override
    public Users getUser(String username) {
        String sql = "select * from users where first_name=:uname";
        Map<String, Object> map = new HashMap<>();
        map.put("uname", username);
        List<Users> list = new ArrayList<>();
        list = namedParameterJdbcTemplate.query(sql, map, new UserMapper());
        Users user = new Users();
        if (list.size() > 0) {
            user = list.get(0);
            return user;
        }
        return null;
    }

    @Override
    public List<Users> ReadByUid(Integer uid) {
        String sql = "select * from users where id=:id";
        Map<String, Object> map = new HashMap<>();
        map.put("id", uid);
        List<Users> list = new ArrayList<>();
        list = namedParameterJdbcTemplate.query(sql, map, new UserMapper());
        Users user = new Users();
        if (list.size() > 0) {
            return list;
        }
        return null;
    }

    @Override
    public List<Users> listAll() {
        String sql = "select * from users order by email asc";
        Map<String, Object> map = new HashMap<>();
        List<Users> list = new ArrayList<>();
        list = namedParameterJdbcTemplate.query(sql, map, new UserMapper());
        Users user = new Users();
        if (list.size() > 0) {
            return list;
        }
        return null;
    }

    @Override
    public List<UsersRole> FindUserRole() {
        String sql = "select r.name as description from users inner join users_roles Ur on users.id = Ur.user_id  inner join roles r on Ur.role_id = r.id";
        Map<String, Object> map = new HashMap<>();
        List<UsersRole> list = new ArrayList<>();
        list = namedParameterJdbcTemplate.query(sql, map, new UsersRoleMapper());

        if (list.size() > 0) {
            return list;
        }
        return null;

    }

    @Override
    public List<UsersRole> FindUserRoleByUser(Users user) {
        String sql = "select r.name as description from users inner join users_roles  Ur on users.id = Ur.user_id  inner join roles r on Ur.role_id = r.id where users.first_name=:username";
        Map<String, Object> map = new HashMap<>();
        map.put("username", user.getFirst_name());
        List<UsersRole> list = new ArrayList<>();
        list = namedParameterJdbcTemplate.query(sql, map, new UsersRoleMapper());

        if (list.size() > 0) {
            return list;
        }
        return null;

    }

    //Get all role names and descriptions
    @Override
    public List<Role> getAllRole() {
        String sql = "select * from roles";
        List<Role> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        list = namedParameterJdbcTemplate.query(sql, map, new RoleMapper());
        if (list.size() > 0) {
            return list;
        }
        return null;
    }


    @Override
    public List<Users> getUserIdbyName(String username) {

        String sql = "select id from users where first_name=:username";
        Map<String, Object> map = new HashMap<>();
        List<Users> list = new ArrayList<>();
        list = namedParameterJdbcTemplate.query(sql, map, new UserMapper());
        if (list.size() > 0) {
            return list;
        }
        return null;
    }

    @Override
    public List<Users> getUserbyName(String username) {

        String sql = "select * from users where first_name=:username";
        Map<String, Object> map = new HashMap<>();
        List<Users> list = new ArrayList<>();
        list = namedParameterJdbcTemplate.query(sql, map, new UserMapper());
        if (list.size() > 0) {
            return list;
        }
        return null;
    }


    @Override
    public List<Users> getUserByEmail(String email) {
        String sql = "select * from  users where email=:email";
        Map<String, Object> map = new HashMap<>();
        map.put("email", email);
        List<Users> list = new ArrayList<>();
        list = namedParameterJdbcTemplate.query(sql, map, new UserMapper());
        if (list.size() > 0) {
            return list;
        }
        return null;
    }


    @Override
    public void DeleteUserById(Integer id) {
        String sql = "delete from users where id=:id";
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void UpdateEnabledStatus(Integer id, int enabled) {
        String sql = "update users set enabled=:enablestatus where id=:id";
        Map<String, Object> map = new HashMap<>();
        map.put("enablestatus", enabled);
        map.put("id", id);
        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public List<Users> getUserByPagination(int pageno) {
        String sql = "select * from users  limit :pageno,10 ";
        Map<String, Object> map = new HashMap<>();
        map.put("pageno", (pageno - 1) * 10);
        List<Users> list = new ArrayList<>();
        list = namedParameterJdbcTemplate.query(sql, map, new UserMapper());
        if (list.size() > 0) {
            return list;
        } else {
            return null;
        }

    }


    @Override
    public List<Users> usingKeywordfindUser(String firstname, String lastname) {
        String sql = "select * from users where first_name like :username or last_name like :lastname";
        List<Users> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("username", "%" + firstname + "%");
        map.put("lastname", "%" + lastname + "%");
        list = namedParameterJdbcTemplate.query(sql, map, new UserMapper());

        if (list.size() > 0) {
            return list;
        }
        return null;
    }

    @Override
    public Integer getCountByUser() {
        String sql = "select count(*) as total from users";
        List<PageNumber> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        int number = 0;
        list = namedParameterJdbcTemplate.query(sql, map, new PageNumberMapper());
        for (PageNumber page : list) {
            number = page.getPagenumber();
        }
        return number;
    }
}
