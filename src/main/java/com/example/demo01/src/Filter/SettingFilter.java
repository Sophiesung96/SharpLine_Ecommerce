package com.example.demo01.src.Filter;

import com.example.springboot_ecommerce.Pojo.Setting;
import com.example.demo01.src.Service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class SettingFilter extends OncePerRequestFilter {
    @Autowired
    SettingService settingService;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String url=httpServletRequest.getRequestURI();
        if(url.endsWith(".css") ||url.endsWith(".js") || url.endsWith(".jpg") || url.endsWith(".png")){
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }
        List<Setting> list=settingService.getGeneralSetting();
        list.forEach(setting->httpServletRequest.setAttribute(setting.getKey(), setting.getValue()));
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
