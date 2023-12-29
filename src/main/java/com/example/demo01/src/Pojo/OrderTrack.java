package com.example.demo01.src.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderTrack {

    private int id;
    private int orderId;
    private String status;
    private Date updatedTime;
    private String notes;


    public String getUpdatedTimeonForm() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        String time = dateFormat.format(this.updatedTime);
        return time;
    }


    public void setUpdatedTimeonForm(String dateString){
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        try{
           this.updatedTime= dateFormat.parse(dateString);
        }catch(ParseException e){
            e.printStackTrace();
        }

    }
}
