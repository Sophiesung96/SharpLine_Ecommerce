package com.example.demo01.src.Service;

import com.example.demo01.src.DAO.CountryDao;
import com.example.demo01.src.Pojo.Country;

import java.util.List;

public interface CountryService {

    public void saveCountry(Country country);
    public Country getByCountryId(int countryId);
    List<Country> findAllByNameOrderByAsc();
    public void DeleteById(Integer id);
    public void updateCountry(Country country);
}
