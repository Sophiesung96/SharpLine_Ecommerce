package com.example.demo01.src.DAO;

import com.example.demo01.src.Pojo.*;

import java.util.List;

public interface ProductDAO {

    public List<Product> listAll();

    public List<Product> listAllOrderByName();

    public List<ProductCBName> selectCategoyrnBrandByName();
    public ProductCBName selectCategoyrnBrandByProductId(int id);

    public void saveProduct(Product product);

    public  List<Product> selectProductByName(Product product);

    public void UpdateEnabledStatus(int id, int enabled);
    public void deleteProductById(int id);
    public Product findById(int id);
    public void saveProductDetails(ProductDetail detail,int Pid);
    public List<ProductDetail> selectProductDetailsById(int id);
    public List<Product> selectProductByPagination(int pageno);
    public List<PageNumber> getPageCount();
    public Product editProductById(int id);
    public void saveExtraImagesofProduct(String name,Product product);

    public List<ProductImage> selectExtraByProductId(int id);
    public void saveEditedProductById(Product product);
    public void UpdateProductImage(ProductImage image,Product product);
    public void UpdateProductDetails(ProductDetail productDetail,Product product);
    public void DeleteProductDetailsByProductId(int productId,int id);
    public  List<Product> getProductByCategoryId(int categoryId,int pageno);
    public Product findByNickName(String nickname);
    public  List<Product> SearchByKeyword(int pageno,String search);

    public List<PageNumber> getFilteredPageCount(String search);

    public List<Product> ProductSearchByKeywordforOrder(int pageno, String search);

}
