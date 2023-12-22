package com.example.demo01.src.Service;

import com.example.demo01.src.DAO.CustomerDao;
import com.example.demo01.src.DAO.ProductDAO;
import com.example.demo01.src.DAO.ShippingRateDAO;
import com.example.demo01.src.Exception.ShippingRateNotFoundException;
import com.example.demo01.src.Pojo.Address;
import com.example.demo01.src.Pojo.Customer;
import com.example.demo01.src.Pojo.Product;
import com.example.demo01.src.Pojo.ShippingRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShippingRateServiceImpl implements  ShippingRateService{
    private static final int DIM_DIVISOR=139;
    @Autowired
    ShippingRateDAO shippingRateDAO;

    @Autowired
    CustomerDao customerDao;

    @Autowired
    ProductDAO productDAO;


    @Override
    public ShippingRate getShippingRateForAddress(Customer customer, Address Defaultaddress) {
        ShippingRate shippingRate=new ShippingRate();
         if(Defaultaddress!=null){
             //address is set to primary address
             // so this address cannot be found in address table
                shippingRate=shippingRateDAO.getShippingRateForAddress(Defaultaddress.getState(), Defaultaddress.getCountryId());

         }else{
              shippingRate=getShippingRateforCustomer(customer);
             return shippingRate;
         }
        return shippingRate;
    }

    @Override
    public ShippingRate getShippingRateforCustomer(Customer customer) {
        Customer customer1=customerDao.findCustomerById(customer.getId());
        ShippingRate shippingRate=new ShippingRate();
        if(customer1!=null){
             shippingRate=shippingRateDAO.getShippingRateForAddress(customer1.getState() ,customer1.getCountryId());

        }
        return shippingRate;
    }

    @Override
    public float calculateShippingCost(Integer productId, Integer countryId, String state) throws ShippingRateNotFoundException {
        ShippingRate shippingRate=shippingRateDAO.getShippingRateForAddress(state,countryId);
        //calculate the shipping cost and set it manually
        if(shippingRate==null){
            throw new ShippingRateNotFoundException("No shipping rate found for the given"+
                    "destination. You have to enter shipping cost manually");
        }
        Product product=productDAO.findById(productId);
        float dimWeight=(product.getWeight()*product.getHeight()*product.getWidth())/DIM_DIVISOR;
        float finalWeight=product.getWeight()>dimWeight ? product.getWeight():dimWeight;

        return finalWeight*shippingRate.getRate();

    }



}
