package com.example.demo01.src.Pojo;

import lombok.*;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@ToString
public class Brand {
    @NonNull
    private Integer id;
    private String name;
    private String logo;
    private String parentname;
    private Constants constants;


    public String getLogoPath(){
        this.constants=new Constants();
        if(this.id<0|| this.logo==null){
            return "/image/default.jpeg";
        }
        else{

            return constants.getS3BaseUri()+"/brand-logos/"+this.id+"/"+this.logo;
        }
    }
}
