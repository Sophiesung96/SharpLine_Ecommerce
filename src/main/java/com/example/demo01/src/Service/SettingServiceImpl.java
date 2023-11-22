package com.example.demo01.src.Service;

import com.example.springboot_ecommerce.DAO.SettingDAO;
import com.example.springboot_ecommerce.Pojo.EmailSettingBag;
import com.example.springboot_ecommerce.Pojo.GeneralSettingBag;
import com.example.springboot_ecommerce.Pojo.Setting;
import com.example.springboot_ecommerce.Pojo.SettingCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettingServiceImpl implements SettingService{

    @Autowired
    SettingDAO settingDAO;

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
}
