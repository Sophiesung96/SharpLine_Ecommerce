package com.example.demo01.src.Service;

import com.example.springboot_ecommerce.DAO.CountryDao;
import com.example.springboot_ecommerce.Pojo.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CountryServiceImpl implements CountryService{
    @Autowired
    CountryDao countrydao;

    @Override
    public List<Country> findAllByNameOrderByAsc() {
        List<Country> list=new ArrayList<>();
        list=countrydao.findAllByOrderByNameAsc();
        return list;
    }

    @Override
    public void saveCountry(Country country) {
        countrydao.saveCountry(country);

    }

    @Override
    public Country getByCountryId(Country country) {
        return countrydao.getByCountryId(country);
    }

    @Override
    public void DeleteById(Integer id) {
        countrydao.DeleteById(id);
    }

    @Override
    public void updateCountry(Country country) {
        countrydao.updateCountry(country);
    }
}


