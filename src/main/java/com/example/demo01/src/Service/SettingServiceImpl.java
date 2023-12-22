package com.example.demo01.src.Service;

import com.example.demo01.src.DAO.CurrencyDao;
import com.example.demo01.src.DAO.SettingDAO;
import com.example.demo01.src.Pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SettingServiceImpl implements SettingService{

    @Autowired
    SettingDAO settingDAO;

    @Autowired
    CurrencyDao currencyDao;

    @Override
    public List<Setting> listAllSettings() {
        List<Setting> list=settingDAO.listAllSettings();
        return list;
    }

    @Override
    public List<Setting> getGeneralSetting() {

        return settingDAO.findByTwoCategory(SettingCategory.GENERAL,SettingCategory.CURRENCY);
    }

   public void saveAll(Iterable<Setting> setting){
        settingDAO.saveIterable(setting);
   }

    @Override
    public List<Setting> findByTwoCategory(SettingCategory category1, SettingCategory category2) {
        return null;
    }

    @Override
    public EmailSettingBag getEmailSettings() {
        List<Setting> list=settingDAO.findByTwoCategory(SettingCategory.MAILTEMPLATE,SettingCategory.MAILSERVER);

        return new EmailSettingBag(list);
    }

    @Override
    public List<Setting> findByCategory(SettingCategory category) {
        List<Setting> list=settingDAO.findByCategory(category);
        return list;
    }

    @Override
    public List<Setting> getMailServerSetting() {
        List<Setting>list=settingDAO.findByCategory(SettingCategory.MAILSERVER);
        return list;
    }


    @Override
    public List<Setting> getMailTEMPLATESetting() {
        List<Setting>list=settingDAO.findByCategory(SettingCategory.MAILTEMPLATE);
        return list;
    }

    @Override
    public CurrencySettingBag getCurrencySetting() {
        List<Setting>list=settingDAO.findByCategory(SettingCategory.CURRENCY);
        return new CurrencySettingBag(list);
    }

    @Override
    public PaymentSettingBag getPaymentSetting() {
        List<Setting>list=settingDAO.findByCategory(SettingCategory.PAYMENT);
        return new PaymentSettingBag(list);
    }

    @Override
    public String getCurrencyCode() {
      Setting setting=settingDAO.findByKey("CURRENCY_ID");
        Integer currencyId=Integer.parseInt(setting.getValue());
        Optional<Currency> Optionalcurrency=currencyDao.findById(currencyId);
        String currencyCode=null;
        if (Optionalcurrency.isPresent()) {
            // Currency is present, retrieve the value
            Currency currency = Optionalcurrency.get();
             currencyCode = currency.getCode();
        }
        return currencyCode;
    }


}
