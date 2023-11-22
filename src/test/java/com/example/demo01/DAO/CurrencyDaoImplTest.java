package com.example.demo01.DAO;

import com.example.demo01.src.DAO.CurrencyDao;
import com.example.demo01.src.Pojo.Currency;
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