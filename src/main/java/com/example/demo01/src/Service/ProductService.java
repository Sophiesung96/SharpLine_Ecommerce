package com.example.demo01.src.Service;

import com.example.demo01.src.Pojo.*;

import java.util.List;

public interface ProductService {

    public List<Product> listAll();

    List<ProductCBName> selectCategoyrnBrandByName();
    public ProductCBName selectCategoyrnBrandByProductId(int id);

    List<Product> listAllOrderByName();

    public void saveProduct(Product product);

    public boolean checkUniqueness(Product product);
    public Product findById(int id);
    public void UpdateEnabledStatus(int id, int enabled);
    public List<Product> selectProductByName(Product product);
    public void deleteProductById(int id);
    public void saveProductDetails(ProductDetail detail,int Pid);
    public Product editProductById(int id);
    public void saveExtraImagesofProduct(String name,Product product);
    public List<ProductImage> selectExtraByProductId(int id);
    public List<Product> selectProductByPagination(int pageno);
     List<ProductDetail>  selectProductDetailsById(int id);
    public List<Integer> getPageCount();
    public void saveEditedProductById(Product product);
    public void UpdateProductImage(ProductImage image,Product product);
    public void UpdateProductDetails(ProductDetail detail,Product product);
    public void DeleteProductDetailsByProductId(int productId,int id);
    public List<Product> getProductByCategoryId(int categoryid,int pageno);
    public Product findByNickName(String nickname);

    public  List<Product> SearchByKeyword(int pageno,String search);

    public List<Integer> getFilteredPageCount(String search);
    public List<Product> ProductSearchByKeywordforOrder(int pageno, String search);


    List<Integer> getFilteredPageCount4Order(String keyword);
}

