package com.example.demo01.src.Service;

import com.example.springboot_ecommerce.DAO.CartItemDao;
import com.example.demo01.src.Exception.ShoppingCartException;
import com.example.springboot_ecommerce.Pojo.CartItem;
import com.example.springboot_ecommerce.Pojo.CartItemPName;
import com.example.springboot_ecommerce.Pojo.Customer;
import com.example.springboot_ecommerce.Pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService{
    @Autowired
    CartItemDao cartItemDao;

    public Integer addProduct(Integer productId, Integer quantity, Customer customer){
        Integer updateQuantity=quantity;
        Product product=new Product(productId);
        CartItem cartItem= cartItemDao.findByCustomerAndProduct(customer,product);
        // if the customer already has item in the shopping cart
        if(cartItem!=null){
            updateQuantity=cartItem.getQuantity()+quantity;
            if(updateQuantity>5){
                throw new ShoppingCartException("Could not add more than"+quantity+" "+"items"
                +" because there's already"+cartItem.getQuantity()+" "+
                        "item(s) in your shopping cart"+
                        "Maximum allowed quantity is 5!");

            }        }else{
            cartItem=new CartItem();
            cartItem.setProduct(productId);
            cartItem.setCustomer(customer.getId());
        }
        cartItem.setQuantity(updateQuantity);
        cartItemDao.SaveCartItem(cartItem);
        return updateQuantity;
    }

    @Override
    public void deleteByCustomer(Customer customer, int productId) {
        cartItemDao.deleteByCustomer(customer,productId);
    }

    @Override
    public List<CartItemPName> getJoinedProductnCustomer(Customer customer) {
        List<CartItemPName> list=cartItemDao.getJoinedProductnCustomer(customer);
        return list;
    }

    @Override
    public List<CartItem> listAllCartItem(Customer customer) {
        List<CartItem> list=cartItemDao.findByCustomer(customer);
        return list;
    }
}
