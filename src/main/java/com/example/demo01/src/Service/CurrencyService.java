package com.example.demo01.src.Service;

import com.example.demo01.src.Pojo.Currency;

import java.util.List;
import java.util.Optional;

public interface CurrencyService {

    public Optional<Currency> findById(int id);

    public List<Currency> findAllOrderByNameAsc();
}
