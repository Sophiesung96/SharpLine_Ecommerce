package com.example.demo01.src.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


@Configuration
@EnableWebSecurity
@Order(-1)
public class WebConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

        private static String sqlstatement = "SELECT first_name as name ,password, enabledStatus as enabled from Users WHERE first_name=?";
        private static String sqlquote = "SELECT Users.first_name as name,r.name as role from Users INNER JOIN Users_role Ur on Users.id = Ur.user_id  INNER JOIN role r on Ur.role_id = r.id  WHERE Users.first_name=?";

        @Autowired
        DataSource dataSource;


        @Autowired
        NamedParameterJdbcTemplate namedParameterJdbcTemplate;







        public void configure(HttpSecurity http) throws Exception {
                     http.cors().and().csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/swagger-ui/**").permitAll()
                    .antMatchers("/swagger-resources/**").permitAll()
                    .antMatchers("/v3/api-docs/**").permitAll()
                    .antMatchers("/users/**")
                    .hasAuthority("Admin")
                    .antMatchers("/categories/**")
                    .hasAnyAuthority("Admin", "Editor")
                    .antMatchers("/brands/**")
                    .hasAnyAuthority("Admin", "Editor")
                    .antMatchers("/products/**")
                    .hasAnyAuthority("Admin", "Editor", "SalesPerson", "Shipper")
                    .antMatchers("/questions/**")
                    .hasAnyAuthority("Admin", "Shipper")
                    .antMatchers("/customers/**","/get_shipping_cost")
                    .hasAnyAuthority("Admin", "SalesPerson")
                    .antMatchers("/shipping/**")
                    .hasAnyAuthority("Admin", "SalesPerson")
                    .antMatchers("/orders/**")
                    .hasAnyAuthority("Admin", "Editor", "Shipper")
                    .antMatchers("/report/**")
                    .hasAnyAuthority("Admin", "SalesPerson")
                    .antMatchers("/article/**")
                    .hasAnyAuthority("Admin", "Editor")
                    .antMatchers("/settings/**")
                    .hasAuthority("Admin")
                    .antMatchers("/index").permitAll()
                    .antMatchers("/listproducts/**").permitAll()
                    .antMatchers("/p/**").permitAll()
                    .antMatchers("/c/**").permitAll()
                    .antMatchers("/keyword/**").permitAll()
                    .antMatchers("/register/**").permitAll()
                    .antMatchers("/verify/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin(
                    )
                    .loginPage("/login")
                    .defaultSuccessUrl("/home")
                    .permitAll()
                    .and()
                    .rememberMe()
                    .and()
                    .logout().permitAll();

                     http.headers().frameOptions().sameOrigin();

        }


        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {

            //create a password
            String rawpassword = "testing";
            String encodepassword = passwordEncoder().encode(rawpassword);
            //Update the original password in database as an encoded password
            String encodesql = "UPDATE Users SET password=:userpassword WHERE first_name=:username";
            Map<String, Object> map = new HashMap<>();
            map.put("userpassword", encodepassword);
            map.put("username", "testing10");
            namedParameterJdbcTemplate.update(encodesql, map);
            //This configuration is temporary


            auth.jdbcAuthentication()
                    .dataSource(dataSource)
                    .passwordEncoder(passwordEncoder())
                    .usersByUsernameQuery(sqlstatement)
                    .authoritiesByUsernameQuery(sqlquote);


        }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/springfox-swagger-ui/**","/product-images/**","/category-image/**","/c/**","/**/image/**", "/**/css/**", "/static/front/**", "/**/Js/**", "/webjars/**");
    }


}
