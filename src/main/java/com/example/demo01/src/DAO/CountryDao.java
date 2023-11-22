package com.example.demo01.src.DAO;

import com.example.demo01.src.Pojo.Country;

import java.util.List;

public interface CountryDao {

    public List<Country> findAllByOrderByNameAsc();
    public void saveCountry(Country country);
    public Country getByCountryId(Country country);
    public void DeleteById(Integer id);
    public void updateCountry(Country country);

    public Country findByCountryCode(String countryCode);
}
