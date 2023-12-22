package com.example.demo01.src.DAO;

import com.example.demo01.src.Pojo.Country;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CountryDaoImplTest {

    @SpyBean
    CountryDao countryDao;

    @Test
    public void testfindAllByOrderByNameAsc(){
     List<Country> list=countryDao.findAllByOrderByNameAsc();
     list.stream().forEach(country->assertNotNull(country));
    }

    @Test
    @Transactional
    public void testUpdateCountry(){
        Country country=new Country();
        country.setId(1);
        country.setName("United Kingdom");
        country.setCode("R.O.C ");
        countryDao.updateCountry(country);
        Country newCountry= countryDao.findByCountryCode("R.O.C ");
        assertEquals(country.getCode(),newCountry.getCode());
    }

    @Test
    @Transactional
    public void DeleteById(){
      countryDao.DeleteById(2);
      Country country= countryDao.getByCountryId(2);
      assertNull(country);
    }

}