package com.example.demo01.src.Controller;

import com.example.springboot_ecommerce.Pojo.Country;
import com.example.springboot_ecommerce.Pojo.Currency;
import com.example.springboot_ecommerce.Pojo.State;
import com.example.springboot_ecommerce.Service.SettingService;
import com.example.springboot_ecommerce.Service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StateRestController {

    @Autowired
    StateService stateService;


    @GetMapping("/states/list")
    public ResponseEntity<List<Country>> listAll() {
        List<Country> list = new ArrayList<>();
        list = stateService.listAll();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @GetMapping("/states/list_by_country/{id}")
    public List<State> listByCountry(@PathVariable int id) {
        List<State> list = new ArrayList<>();
        list = stateService.findByCountryOrderByNameAsc(id);

        return list;
    }

    @PostMapping("/states/save")
    public String save(@RequestBody State state) {
        stateService.save(state);

        State state1 = stateService.getCategoryById(state);
        return String.valueOf(state1.getId());
    }

    @DeleteMapping("/states/delete/{id}")
    public void delete(@PathVariable int id) {
        stateService.delete(id);
    }


    @PostMapping("/states/update")
    public void update(@RequestBody State state) {
        stateService.updateStateByCategoryId(state);
    }



}
