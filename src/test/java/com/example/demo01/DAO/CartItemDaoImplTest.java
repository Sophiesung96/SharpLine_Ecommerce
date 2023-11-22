package com.example.demo01.src.test.java.com.example.springboot_ecommerce.DAO;

import com.example.springboot_ecommerce.Pojo.CartItem;
import com.example.springboot_ecommerce.Pojo.CartItemPName;
import com.example.springboot_ecommerce.Pojo.Customer;
import com.example.springboot_ecommerce.Pojo.Product;
import com.example.springboot_ecommerce.Service.ShoppingCartService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CartItemDaoImplTest {

    @Autowired
    CartItemDao cartItemDao;

    @Autowired
    ShoppingCartService shoppingCartService;


    @Test
    public void testFindByCustomerReturnsEmptyList() {
        // Create a customer with ID 1
        Customer customer = new Customer();
        customer.setId(1);
        List<CartItem> result = new ArrayList<>();
     CartItem cartItem=new CartItem();
     cartItem.setCustomer(1);
     cartItem.setProduct(1);
     cartItem.setQuantity(1);
     result.add(cartItem);
        // Mock the behavior of cartItemDao.findByCustomer to return an empty list
        Mockito.when(cartItemDao.findByCustomer(customer)).thenReturn(result);
        for(CartItem c:result){
            assertEquals(c.getCustomer(),1);
        }
    }

    @Test
    public void testfindByCustomerAndProduct(){
        Customer customer=new Customer();
        customer.setId(1);
        Product product=new Product();
        product.setId(1);
        CartItem cartItem=cartItemDao.findByCustomerAndProduct(customer,product);

            assertEquals(cartItem.getId(),1);
            assertEquals(cartItem.getCustomer(),1);
            assertEquals(cartItem.getProduct(),1);
    }
    @Test
    @Transactional
    public void updateQuantity(){
        cartItemDao.updateQuantity(1,1,1);
    }


    @Test
    public void testSave(){
        CartItem cartItem=new CartItem();
        cartItem.setQuantity(2);
        cartItem.setCustomer(3);
        cartItem.setProduct(5);
        cartItemDao.SaveCartItem(cartItem);
    }

    @Test
    public void testgetJoinedProductnCustomer(){
        Customer customer=new Customer();
        customer.setId(10);
        List<CartItemPName> list=cartItemDao.getJoinedProductnCustomer(customer);
        list.forEach(cartItemPName ->
                assertEquals("Oppo Find X4 Pro",cartItemPName.getProductName()));
        list.forEach(cartItemPName -> assertEquals("Oppo Find X4 Pro", cartItemPName.getProductAlias()));
    }





}