package com.example.demo01.src.Controller;

import com.example.demo01.src.Pojo.Customer;
import com.example.demo01.src.Pojo.State;
import com.example.demo01.src.Pojo.Users;
import com.example.demo01.src.Service.CustomerService;
import com.example.demo01.src.Service.StateService;

import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Customer RestController")
public class CustomerRestController {

    @Autowired
    CustomerService customerService;

    @Autowired
    StateService stateService;

    @PostMapping("/register/check_UniqueEmail")
    @Operation(summary = "Check Email", description = "Check customer's email uniqueness")
    public ResponseEntity<String> getEmailUnique(@RequestBody Customer customer){
       boolean unique= customerService.checkEmailUnique(customer.getEmail());

        return ResponseEntity.ok().body(unique?"ok":"Duplicated");
    }

    @GetMapping("/register/listcountry/{categoryId}")
    @Operation(description = "this is for getting states by category id")
    public List<State> listCountry4Registration(@PathVariable int categoryId ){
        List<State> list=stateService.findByCountryOrderByNameAsc(categoryId);
        return list;
    }
}
