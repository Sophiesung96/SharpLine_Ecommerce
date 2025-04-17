package com.example.demo01.src.DAO;

import com.example.demo01.src.Pojo.PageNumber;
import com.example.demo01.src.Pojo.Product;
import com.example.demo01.src.Pojo.ProductDetail;
import com.example.demo01.src.Pojo.ProductImage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ProductDAOImplTest {



    @SpyBean
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
        Product product=productDAO.editProductById(id);
        assertEquals(0,product.getEnabled());

    }
    @Test
    @Transactional
    public void deleteProductById(){
        int id=1;
        productDAO.deleteProductById(id);
      Product product= productDAO.editProductById(id);
      assertNotNull(product);
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
    @Test
    @Transactional
    public void UpdateReviewCountandAverageRating(){
        int productId=6;
        Product product=new Product();
        productDAO.UpdateReviewCountandAverageRating(productId);
       product=productDAO.editProductById(productId);
      assertEquals(product.getAverageRating(),4);
    }

    @Test
    public void testgetPageCountForCategoriesWithParentId(){
        int parentId=2;
        List<PageNumber> list = productDAO.getPageCountForCategoriesWithParentId(parentId);
        list.forEach(num-> System.out.println(num.getPagenumber()));

    }

}