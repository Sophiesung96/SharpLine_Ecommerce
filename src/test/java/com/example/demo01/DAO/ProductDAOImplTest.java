package com.example.demo01.src.test.java.com.example.springboot_ecommerce.DAO;

import com.example.springboot_ecommerce.Pojo.Product;
import com.example.springboot_ecommerce.Pojo.ProductDetail;
import com.example.springboot_ecommerce.Pojo.ProductImage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductDAOImplTest {



    @MockBean
    ProductDAO productDAO;

    @Test
    public void selectall() {
        List<Product> list = new ArrayList<>();
        list = productDAO.listAll();
        assertNotNull(list);
    }

    @Test
    public void selectProductByname() {
        List<Product> list = new ArrayList<>();
        Product product = new Product();
        product.setId(1);
        product.setBrandId(2);
        product.setCategoryId(7);
        product.setEnabled(1);
        product.setAlias("samsung Galaxy A31");
        product.setShortDescription("A Goog smartphone from Samsung");
        product.setName("Samsung Galaxy A31");
        list=productDAO.selectProductByName(product);
        Product p = list.get(0);
        assertNotNull(p);
        assertEquals(1, p.getId());
        assertEquals(2, p.getBrandId());
        assertEquals(7, p.getCategoryId());
        assertEquals("samsung Galaxy A31", p.getName());
        assertEquals("A Goog smartphone from Samsung", p.getAlias());
        assertEquals(1, p.getEnabled());

    }
    @Test
    @Transactional
    public void updateUpdateEnabledStatus(){
        int id=1;
        int enabled=0;
        productDAO.UpdateEnabledStatus(id,enabled);

    }
    @Test
    @Transactional
    public void deleteProductById(){
        int id=1;
        productDAO.deleteProductById(id);
    }

    @Test
    public void insertProductDetail(){
        ProductDetail p=new ProductDetail();
        p.setName("dell");
        p.setValue("test");
        productDAO.saveProductDetails(p,1);
    }

    @Test
    @Transactional
    public void insertExtraImage(){
        ProductImage productImage=new ProductImage();
       Product p=new Product();
       p.setId(1);
        productImage.setName("testing01");
        productDAO.saveExtraImagesofProduct(productImage.getName(),p);

    }
    @Test
    public void selectProductDetailById(){
        Product product=new Product();
        product.setId(1);
        List<ProductDetail> list=new ArrayList<>();
        Mockito.when(productDAO.selectProductDetailsById(product.getId())).thenReturn(list);
        for(ProductDetail p:list){
            assertEquals(1,p.getProductId());
            assertEquals("dell",p.getName());
            assertEquals("test",p.getValue());

        }


    }


}