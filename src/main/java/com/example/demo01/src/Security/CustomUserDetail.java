package com.example.demo01.src.Security;

import com.example.demo01.src.Pojo.CartItem;
import com.example.demo01.src.Pojo.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserDetail implements UserDetails {

    @Autowired
    Customer customer;

    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;


    public CustomUserDetail(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Change this to return true if account is not expired
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Change this to return true if account is not locked
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Change this to return true if credentials are not expired
    }

    @Override
    public boolean isEnabled() {
        return true; // Change this to return true if the user is enabled
    }

  public void setCustomer(Customer customer){
        this.customer=customer;
  }

    public Customer getCustomer() {
        return customer;
    }
}
