package com.example.demo01.src.Controller;

import com.example.demo01.src.Configuration.JWTUtil;
import com.example.demo01.src.Pojo.Category;
import com.example.demo01.src.Pojo.Users;
import com.example.demo01.src.Security.CustomUserDetailsService;
import com.example.demo01.src.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.List;

@RestController
@Slf4j
public class UserRestController {

    @Autowired
    UserService userService;

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    CustomUserDetailsService service;

    @Autowired
    AuthenticationManager authenticationManager;

    @RequestMapping(value = "/checkEmail", method = RequestMethod.POST)
    public String CheckDuplicateEmail(@RequestBody Users user) {
        boolean unique;

        unique = userService.isEmailUnique(user.getEmail());
        System.out.println(unique);
        return unique ? "Unique" : "Duplicated";


    }


}
