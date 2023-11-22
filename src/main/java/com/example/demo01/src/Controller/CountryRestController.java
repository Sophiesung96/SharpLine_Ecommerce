package com.example.demo01.src.Controller;

import com.example.springboot_ecommerce.Pojo.Country;
import com.example.springboot_ecommerce.Service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CountryRestController {

    @Autowired
    CountryService countryService;

    @GetMapping("/countries/list")
    public ResponseEntity<List<Country>> listAll(){
        List<Country> list=new ArrayList<>();
        list=countryService.findAllByNameOrderByAsc();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }
    @PostMapping("/countries/save")
    public  String save(@RequestBody Country country){
        countryService.saveCountry(country);
       Country country1= countryService.getByCountryId(country);
     return String.valueOf(country1.getId());
    }

    @PostMapping("/countries/update")
    public  String update(@RequestBody Country country){
        countryService.updateCountry(country);
        Country country1= countryService.getByCountryId(country);
        return String.valueOf(country1.getId());
    }

    @DeleteMapping("/countries/delete/{id}")
    public void delete(@PathVariable int id){
        countryService.DeleteById(id);
    }
}
