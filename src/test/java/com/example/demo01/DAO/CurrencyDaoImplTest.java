package com.example.demo01.src.test.java.com.example.springboot_ecommerce.DAO;

import com.example.springboot_ecommerce.Pojo.Currency;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CurrencyDaoImplTest {
    @Autowired
    CurrencyDao currencyDao;

    @Test
    public void findall(){
        List<Currency> list=currencyDao.findAllOrderByNameAsc();
        for(Currency c:list){
            assertNotNull(c);

        }
    }

}