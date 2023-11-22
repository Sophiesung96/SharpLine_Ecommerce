package com.example.demo01.src.Controller;

import com.example.springboot_ecommerce.Pojo.Users;
import com.example.springboot_ecommerce.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserRestController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/checkEmail", method = RequestMethod.POST)
    public String CheckDuplicateEmail(@RequestBody Users user) {
        boolean unique;

        unique = userService.isEmailUnique(user.getEmail());
        System.out.println(unique);
        return unique ? "Unique" : "Duplicated";


    }


}
