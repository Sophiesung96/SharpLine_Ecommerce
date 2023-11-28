package com.example.demo01.src.Security;

import com.example.demo01.src.DAO.CustomerDao;
import com.example.demo01.src.Pojo.Customer;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerDao customerDao;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public CustomUserDetail loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch user details from your repository
        Customer customer = customerDao.findByName(username);

        if (customer == null) {
            throw new UsernameNotFoundException("User not found");
        }
        //if the customer's password is not encoded
        if(!customer.getPassword().startsWith("$2")){
            String encodedPassword= passwordEncoder.encode(customer.getPassword());
            customerDao.encodePasswordByCustomerId(customer.getId(), encodedPassword);
            customer.setPassword(encodedPassword);
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
