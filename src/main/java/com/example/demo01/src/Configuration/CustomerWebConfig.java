package com.example.demo01.src.Configuration;

import com.example.demo01.src.Security.CustomUserDetailsService;
import com.example.demo01.src.Security.CustomerOAuth2UserService;
import com.example.demo01.src.Security.DatabaseLoginSuccessHandler;
import com.example.demo01.src.Security.OAuth2LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


@Configuration
@EnableWebSecurity
@Order(1)
public class CustomerWebConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private CustomerOAuth2UserService oAuth2UserService;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    @Autowired
    private DatabaseLoginSuccessHandler databaseLoginSuccessHandler;



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http

                .csrf().disable()
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers("/oauth2/**").permitAll()
                .antMatchers("/customer/**", "/checkout", "placeOrder", "/addressBook", "/cart","/process_paypal_order").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/customerLogin").successHandler(databaseLoginSuccessHandler)
                .permitAll()
                .and()
                .oauth2Login()
                .loginPage("/customerLogin")
                .userInfoEndpoint()
                .userService(oAuth2UserService)
                .and()
                .successHandler(oAuth2LoginSuccessHandler)
                .and()
                .logout().permitAll()
                .and()
                .rememberMe()
                .key("1234567890_aBcDeFgHiJkLmNoPqRsTuVwXyZ")
                .tokenValiditySeconds(14 * 24 * 60 * 60);

      //  http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    }
        @Override
        protected void configure (AuthenticationManagerBuilder auth) throws Exception {


            //create a password
            String rawpassword = "sanyatesting";
            String encodepassword = passwordEncoder.encode(rawpassword);
            //Update the original password in database as an encoded password
            String encodesql = "UPDATE customers SET password=:userpassword WHERE first_name=:username";
            Map<String, Object> map = new HashMap<>();
            map.put("userpassword", encodepassword);
            map.put("username", "Sanya");
            namedParameterJdbcTemplate.update(encodesql, map);
            //This configuration is temporary


            auth.jdbcAuthentication()
                    .dataSource(dataSource)
                    .passwordEncoder(passwordEncoder)
                    .usersByUsernameQuery("SELECT CONCAT(first_name, ' ', last_name) AS username,password,enabled from customers where first_name=?")
                    .authoritiesByUsernameQuery("SELECT ?, 'ROLE_USER' UNION SELECT NULL, 'ROLE_USER'");
        }


        @Override
        public void configure (WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/springfox-swagger-ui/**", "/product-images/**", "/category-image/**", "/c/**", "/**/image/**", "/**/css/**", "/static/front/**", "/**/Js/**", "/webjars/**");
        }

        @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
        public AuthenticationManager addAuthenticationBean () throws Exception {
            return super.authenticationManagerBean();
        }




}

