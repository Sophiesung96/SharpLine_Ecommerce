package com.example.demo01.src.Service;

import com.example.demo01.src.DAO.CurrencyDao;
import com.example.demo01.src.Pojo.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurrencyServiceImpl implements CurrencyService{
    @Autowired
    CurrencyDao currencyDao;

    @Override
    public Optional<Currency> findById(int id) {


        return currencyDao.findById(id);
    }

    @Override
    public List<Currency> findAllOrderByNameAsc() {
        return currencyDao.findAllOrderByNameAsc();
    }
}
