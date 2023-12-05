package com.example.demo01.src.Pojo;

import java.util.List;

public class PaymentSettingBag extends  SettingBag {
    public PaymentSettingBag(List<Setting> settingList) {
        super(settingList);
    }

    public String getURL() {
        return super.getValue("PAYPAL_API_BASE_URL");
    }

    public String getClientID() {
        return super.getValue("PAYPAL_API_CLIENT_ID");
    }

    public String getClientSecret() {
        return super.getValue("PAYPAL_API_CLIENT_SECRET");
    }

}
