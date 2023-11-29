package com.example.demo01.src.Service;

import com.example.demo01.src.Pojo.*;

import java.util.List;

public interface SettingService {

    public List<Setting> listAllSettings();
    public List<Setting> findByCategory(SettingCategory category);
    public List<Setting> getGeneralSetting();
    public List<Setting> getMailServerSetting();
    public List<Setting> getMailTEMPLATESetting();
    public void saveAll(Iterable<Setting> setting);
    public List<Setting> findByTwoCategory(SettingCategory category1,SettingCategory category2 );
    public EmailSettingBag getEmailSettings();
    public CurrencySettingBag  getCurrencySetting();
}
