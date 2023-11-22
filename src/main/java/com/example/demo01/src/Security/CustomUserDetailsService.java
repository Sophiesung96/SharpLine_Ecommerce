package com.example.demo01.src.Security;

import com.example.springboot_ecommerce.DAO.CustomerDao;
import com.example.springboot_ecommerce.Pojo.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerDao customerDao;

    @Override
    public CustomUserDetail loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch user details from your repository
        Customer customer = customerDao.findByName(username);

        if (customer == null) {
            throw new UsernameNotFoundException("User not found");
        }
// Create a CustomUserDetails instance using your class
        CustomUserDetail userDetails = new CustomUserDetail(
                customer.getFirstName(), // Use appropriate attribute as username
                customer.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")
                ));



        return userDetails;
    }

}
