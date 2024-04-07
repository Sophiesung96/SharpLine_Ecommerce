package com.example.demo01.src.Controller;

import com.example.demo01.src.Pojo.Country;
import com.example.demo01.src.Service.CountryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    @Operation(summary = "Get all Country List" ,description = "Get all Country List")
    public ResponseEntity<List<Country>> listAll(){
        List<Country> list=new ArrayList<>();
        list=countryService.findAllByNameOrderByAsc();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }
    @PostMapping("/countries/save")
    @Operation(summary = "Save Country" ,description = "Save the newly created Country")
    public  String save(@RequestBody Country country){
        countryService.saveCountry(country);
       Country country1= countryService.getByCountryId(country.getId());
     return String.valueOf(country1.getId());
    }

    @PostMapping("/countries/update")
    public  String update(@RequestBody Country country){
        countryService.updateCountry(country);
        Country country1= countryService.getByCountryId(country.getId());
        return String.valueOf(country1.getId());
    }

    @DeleteMapping("/countries/delete/{id}")
    public void delete(@PathVariable int id){
        countryService.DeleteById(id);
    }
}
