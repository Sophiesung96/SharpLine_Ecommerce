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
public class AddressController {

    @Autowired
    AddressService addressService;

    @Autowired
    CustomerService customerService;

    @Autowired
    CountryService countryService;

    @GetMapping("/addressBook")
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
    public String CreateNewaddress(@ModelAttribute Address address, RedirectAttributes rs){
        addressService.CreateNewCustomer(address,address.getCustomerId());
        rs.addFlashAttribute("message","You have successfully added a new address!");
        return "redirect:/addressBook";
    }
    @GetMapping("/edit/{id}/{customerId}")
    public String ShoweditAddressForm(@PathVariable int id, @PathVariable int customerId,Model model){
        Address address=addressService.ShowCustomerforedit(id,customerId);
        model.addAttribute("address",address);
        List<Country> list=countryService.findAllByNameOrderByAsc();
        model.addAttribute("countryList",list);
        return "address_edit";
    }

    @PostMapping("/update/customer/address")
    public String editAddress(@ModelAttribute Address address,RedirectAttributes rs){
          addressService.updateAddress(address);
        rs.addFlashAttribute("message","You have successfully edited a new address!");
        return "redirect:/addressBook";
    }

    @GetMapping("/address/delete/{id}/{customerId}")
    public String deleteAddress(@PathVariable int id, @PathVariable int customerId,RedirectAttributes rs){
        addressService.deleteAddress(id,customerId);
        rs.addFlashAttribute("message","You have successfully deleted address"+id+" !");
        return "redirect:/addressBook";
    }

    @GetMapping("/address/default/{id}")
    public String setDefaultAddress(@PathVariable int id,HttpServletRequest request,
                                    @RequestParam(required = false,name = "redirect") String redirect){
        Customer customer=getAuthenticatedCustomer(request);
        addressService.setDefaultAddress(id,customer.getId());
       String redirectOption=redirect;
        log.info("redirectOption:{}",redirectOption);
        String redirectURL="redirect:/addressBook";
        if("cart".equals(redirectOption)){
            redirectURL="redirect:/cart";
        }
        return redirectURL;

    }



}
