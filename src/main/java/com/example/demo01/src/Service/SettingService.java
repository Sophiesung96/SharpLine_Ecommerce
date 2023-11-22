package com.example.demo01.src.Service;

import com.example.springboot_ecommerce.Pojo.EmailSettingBag;
import com.example.springboot_ecommerce.Pojo.GeneralSettingBag;
import com.example.springboot_ecommerce.Pojo.Setting;
import com.example.springboot_ecommerce.Pojo.SettingCategory;

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
}
