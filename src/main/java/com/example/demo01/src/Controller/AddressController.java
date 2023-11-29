package com.example.demo01.src.Controller;

import com.example.demo01.src.Configuration.MailConfiguration;
import com.example.demo01.src.Exception.CustomerNotFoundException;
import com.example.demo01.src.Pojo.Address;
import com.example.demo01.src.Pojo.Country;
import com.example.demo01.src.Pojo.Customer;
import com.example.demo01.src.Service.AddressService;
import com.example.demo01.src.Service.CountryService;
import com.example.demo01.src.Service.CustomerService;
import com.example.demo01.src.Pojo.Address;
import com.example.demo01.src.Pojo.Country;
import com.example.demo01.src.Pojo.Customer;
import com.example.demo01.src.Service.AddressService;
import com.example.demo01.src.Service.CountryService;
import com.example.demo01.src.Service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@Slf4j
@Tag(name = "AddressController")
public class AddressController {

    @Autowired
    AddressService addressService;

    @Autowired
    CustomerService customerService;

    @Autowired
    CountryService countryService;

    @GetMapping("/addressBook")
    @Operation(summary = "Show all addresses page", description = "Show all addresses page")
    public String showAddressBook(Model model, HttpServletRequest request){
        Customer customer=getAuthenticatedCustomer(request);
        List<Address> list=addressService.findByCustomer(customer);
        int userPrimaryDefault=1;
        for(Address address:list){
            if(address.isDefaultforShopping()==1){
                userPrimaryDefault=0;
                break;
            }
            else{
                userPrimaryDefault=1;

            }
        }
        model.addAttribute("list",list);
        model.addAttribute("customer",customer);
        model.addAttribute("userPrimaryDefault",userPrimaryDefault);
        return "addresses";
    }

    private Customer getAuthenticatedCustomer(HttpServletRequest request) {
        String email = MailConfiguration.getEmailOfAuthenticatedCustomer(request);
        if (email == null) {
            throw new CustomerNotFoundException("No Aunthenticated Customer");
        }
        if (customerService.getCustomerByEmail(email) != null) {
            return customerService.getCustomerByEmail(email);

        } else {
            String userName = email;
            Customer customer = customerService.getCustomerByfullName(userName);
            log.info(customer.getFirstName());
            return customer;

        }
    }

    @GetMapping("/add/customer/address")
    @Operation(summary = "Show create new address page", description = "Show create new address page")
    public String showAddingaddress(Model model,HttpServletRequest request){
        Address address=new Address();
        model.addAttribute("address",address);
        Customer customer=getAuthenticatedCustomer(request);
        List<Country> list=countryService.findAllByNameOrderByAsc();
        model.addAttribute("countryList",list);
        model.addAttribute("customer",customer);
        return "address_form";
    }

    @PostMapping("/create/customer/address")
    @Operation(summary = "Create new address", description = "Create new customer address")
    public String CreateNewaddress(@ModelAttribute Address address, RedirectAttributes rs, @RequestParam(required = false,name = "redirect") String redirect){
        addressService.CreateNewCustomer(address,address.getCustomerId());
        String redirectOption=redirect;
        log.info("redirectOption:{}",redirectOption);
        String redirectURL="redirect:/addressBook";
        if("checkout".equals(redirectOption)){
            redirectURL+="?redirect=checkout";
        }
        rs.addFlashAttribute("message","You have successfully added a new address!");
        return redirectURL;


    }

    @GetMapping("/edit/{id}/{customerId}")
    @Operation(summary = "show edit address page", description = "Display edit address page")
    public String ShoweditAddressForm(@PathVariable int id, @PathVariable int customerId,Model model){
        Address address=addressService.ShowCustomerforedit(id,customerId);
        model.addAttribute("address",address);
        List<Country> list=countryService.findAllByNameOrderByAsc();
        model.addAttribute("countryList",list);
        return "address_edit";
    }


    @PostMapping("/update/customer/address")
    @Operation(summary = "Update edit address page", description = "Update newly edited address")
    public String editAddress(@ModelAttribute Address address,RedirectAttributes rs){
          addressService.updateAddress(address);
        rs.addFlashAttribute("message","You have successfully edited a new address!");
        return "redirect:/addressBook";
    }

    @GetMapping("/address/delete/{id}/{customerId}")
    @Operation(summary = "Delete an address", description = "Delete an address by AddressId and CustomerId")
    public String deleteAddress(@PathVariable int id, @PathVariable int customerId,RedirectAttributes rs){
        addressService.deleteAddress(id,customerId);
        rs.addFlashAttribute("message","You have successfully deleted address"+id+" !");
        return "redirect:/addressBook";
    }

    @GetMapping("/address/default/{id}")
    @Operation(summary = "Set an address as default", description = "Set an address as default")
    public String setDefaultAddress(@PathVariable int id,HttpServletRequest request,
                                    @RequestParam(required = false,name = "redirect") String redirect){
        Customer customer=getAuthenticatedCustomer(request);
        addressService.setDefaultAddress(id,customer.getId());
       String redirectOption=redirect;
        log.info("redirectOption:{}",redirectOption);
        String redirectURL="redirect:/addressBook";
        if("cart".equals(redirectOption)){
            redirectURL="redirect:/cart";
        }else if ("checkout".equals(redirectOption)){
            redirectURL="redirect:/checkout";
        }
        return redirectURL;

    }



}
