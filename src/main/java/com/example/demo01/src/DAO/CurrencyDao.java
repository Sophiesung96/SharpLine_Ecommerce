package com.example.demo01.src.DAO;

import com.example.demo01.src.Pojo.Currency;

import java.util.List;
import java.util.Optional;

public interface CurrencyDao {

    public List<Currency> findAllOrderByNameAsc();

    Optional<Currency> findById(int id);
}
