package com.example.demo01.src.Service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class OrderServiceImplTest {

    @Autowired
    OrderService orderService;


    @Test
    public void testTotalPage(){
        List<Integer> list=orderService.getTotalPage();
        System.out.println(list);
    }


}