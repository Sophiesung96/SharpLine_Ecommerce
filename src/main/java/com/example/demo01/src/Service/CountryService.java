package com.example.demo01.src.Service;

import com.example.springboot_ecommerce.DAO.CountryDao;
import com.example.springboot_ecommerce.Pojo.Country;

import java.util.List;

public interface CountryService {

    public void saveCountry(Country country);
    public Country getByCountryId(Country country);
    List<Country> findAllByNameOrderByAsc();
    public void DeleteById(Integer id);
    public void updateCountry(Country country);
}
