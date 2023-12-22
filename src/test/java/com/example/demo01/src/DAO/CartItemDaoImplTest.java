package com.example.demo01.src.DAO;

import com.example.demo01.src.Pojo.CartItem;
import com.example.demo01.src.Pojo.CartItemPName;
import com.example.demo01.src.Pojo.Customer;
import com.example.demo01.src.Pojo.Product;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CartItemDaoImplTest {

    @SpyBean
    CartItemDao cartItemDao;

    @Test
    public void  testFindByCustomer(){
        List<CartItem> list=cartItemDao.findByCustomer(new Customer(2));
        list.stream().forEach(cartItem -> assertEquals(2,cartItem.getCustomer()));
    }

    @Test
    public void testFindByCustomerAndProduct(){
        CartItem cartItem= cartItemDao.findByCustomerAndProduct(new Customer(2),new Product(44));
        assertEquals(2,cartItem.getCustomer());
        assertEquals(44,cartItem.getProduct());

    }

    @Test
    @Transactional
    public void testDeleteByCustomerAndProduct(){
        cartItemDao.DeleteByCustomerAndProduct(2,44);
        CartItem cartItem= cartItemDao.findByCustomerAndProduct(new Customer(2),new Product(44));
        assertNull(cartItem);

    }

    @Test
    public void testGetJoinedProductnCustomer(){
        List<CartItemPName> list=cartItemDao.getJoinedProductnCustomer(new Customer(2));
        list.stream().forEach(cartItemPName
                -> assertNotNull(cartItemPName));
    }

    @Test
    public void  TestupdateQuantity(){
        cartItemDao.updateQuantity(2,2,43);
    }



}