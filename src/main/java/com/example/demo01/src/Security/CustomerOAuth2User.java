package com.example.demo01.src.Security;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class CustomerOAuth2User implements OAuth2User {
    private String clientName;

    private String fullName;

    private OAuth2User oAuth2User;



    public CustomerOAuth2User(OAuth2User oAuth2User,String clientName) {
        this.oAuth2User = oAuth2User;
        this.clientName=clientName;
    }

    public CustomerOAuth2User(OAuth2User oAuth2User) {
        this.oAuth2User = oAuth2User;
    }




    @Override
    public <A> A getAttribute(String name) {
        return OAuth2User.super.getAttribute(name);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oAuth2User.getAuthorities();
    }

    @Override
    public String getName() {
        return oAuth2User.getAttribute("name");
    }


    public String getEmail() {
        return oAuth2User.getAttribute("email");
    }


    public String getfullName() {
        return fullName!=null?fullName:oAuth2User.getAttribute("name");
    }
    public void  setfullName(String fullName) {
        this.fullName=fullName;
    }

    public String getClientName() {
        return clientName;
    }
}

