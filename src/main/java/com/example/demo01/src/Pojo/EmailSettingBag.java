package com.example.demo01.src.Pojo;

import java.util.List;

public class EmailSettingBag extends GeneralSettingBag{
    public EmailSettingBag(List<Setting> settingList) {
        super(settingList);
    }

    public String getCustomerVerifySubject(){
        return super.getValue("CUSTOMER_VERIFY_SUBJECT");
    }

    public String getCustomerVerifyContent(){
        return super.getValue("CUSTOMER_VERIFY_CONTENT");
    }
    public String getOrderConfirmationSubject(){
        return super.getValue("ORDER_CONFIRMATION_SUBJECT");
    }
    public String gettOrderConfirmationContent(){
        return super.getValue("ORDER_CONFIRMATION_CONTENT");
    }
    public String getFromAddress(){
        return super.getValue("MAIL_FROM");
    }

    public String getSenderName(){
        return super.getValue("MAIL_SENDER_NAME");
    }

}
