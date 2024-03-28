package com.example.demo01.src.DAO;

import com.example.demo01.src.Pojo.*;

import java.util.List;

public interface ProductDAO {

    List<Product> listAll();
    List<PageNumber> getPageCountForCategoriesWithParentId(int parentId);
    List<Product> listAllOrderByName();

    List<ProductCBName> selectCategoyrnBrandByName();
    ProductCBName selectCategoyrnBrandByProductId(int id);

    void saveProduct(Product product);

    List<Product> selectProductByName(Product product);

    void UpdateEnabledStatus(int id, int enabled);
     void deleteProductById(int id);
     Product findById(int id);
     void saveProductDetails(ProductDetail detail,int Pid);
     List<ProductDetail> selectProductDetailsById(int id);
    List<Product> selectProductByPagination(int pageno);
    List<PageNumber> getPageCount();
    Product editProductById(int id);
    void saveExtraImagesofProduct(String name,Product product);

    List<ProductImage> selectExtraByProductId(int id);
    void saveEditedProductById(Product product);
    void UpdateProductImage(ProductImage image,Product product);
    void UpdateProductDetails(ProductDetail productDetail,Product product);
    void DeleteProductDetailsByProductId(int productId,int id);
    List<Product> getProductByCategoryId(int categoryId,int pageno);
    Product findByNickName(String nickname);
    List<Product> SearchByKeyword(int pageno,String search);

    List<PageNumber> getFilteredPageCount(String search);

    List<Product> ProductSearchByKeywordforOrder(int pageno, String search);
    void UpdateReviewCountandAverageRating(int productId);

}
