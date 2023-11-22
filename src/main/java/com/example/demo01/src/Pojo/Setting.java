package com.example.demo01.src.Pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
@Data
@NoArgsConstructor

public class Setting {
    private String key;
    private String value;
    private SettingCategory category;



    public Setting(String key) {
        this.key = key;
    }

    public Setting(String key, String value, SettingCategory category) {
        this.key = key;
        this.value = value;
        this.category = category;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Setting setting = (Setting) o;
        return Objects.equals(key, setting.key);
    }

    @Override
    public int hashCode() {
        final int prime=31;
        int result=1;
        result=prime+result+((key==null)?0:key.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Setting[key="+key+",value="+value+"]";
    }
}
