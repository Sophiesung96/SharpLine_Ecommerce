package com.example.demo01.src.Service;

import com.example.demo01.src.DAO.ProductDAO;
import com.example.demo01.src.Pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductDAO productDAO;

    @Override
    public List<Product> listAll() {
        List<Product> list = new ArrayList<>();
        list = productDAO.listAll();
        if (list.size() > 0) {
            return list;
        }
        return null;
    }

    @Override
    public List<ProductCBName> selectCategoyrnBrandByName() {
        List<ProductCBName> list = new ArrayList<>();
        list = productDAO.selectCategoyrnBrandByName();
        return list;
    }

    @Override
    public List<Product> listAllOrderByName() {
        List<Product> list = new ArrayList<>();
        list = productDAO.listAll();
        if (list.size() > 0) {
            return list;
        }
        return null;
    }

    @Override
    public void saveProduct(Product product) {
        if (product.getId() == null) {
            product.setCreatedTime(new Date());
        }
        if (product.getAlias() == null || product.getAlias().isEmpty()) {
            String defaultAlias = product.getName();
            product.setAlias(defaultAlias);
        } else {
            product.setAlias(product.getAlias().replaceAll("", "-"));
        }
        product.setUpdatedTime(new Date());

        productDAO.saveProduct(product);
    }

    @Override
    public boolean checkUniqueness(Product product) {
        List<Product> list=new ArrayList<>();
        list = productDAO.selectProductByName(product);
        return list == null;
    }

    @Override
    public void UpdateEnabledStatus(int id, int enabled) {
        productDAO.UpdateEnabledStatus(id, enabled);

    }

    @Override
    public List<Product> selectProductByName(Product product) {
        List<Product> list=new ArrayList<>();
        list=productDAO.selectProductByName(product);
        return list;
    }

    @Override
    public void deleteProductById(int id) {
        productDAO.deleteProductById(id);
    }

    @Override
    public void saveProductDetails(ProductDetail detail, int Pid) {
        productDAO.saveProductDetails(detail,Pid);
    }

    @Override
    public Product editProductById(int id) {
        Product product=productDAO.editProductById(id);
        return product;
    }

    @Override
    public ProductCBName selectCategoyrnBrandByProductId(int id) {
        ProductCBName productCBName=new ProductCBName();
        productCBName=productDAO.selectCategoyrnBrandByProductId(id);
        return productCBName;
    }

    @Override
    public void saveExtraImagesofProduct(String name, Product product) {
        productDAO.saveExtraImagesofProduct(name,product);
    }

    @Override
    public List<ProductImage> selectExtraByProductId(int id) {
        List<ProductImage> list=new ArrayList<>();
        list=productDAO.selectExtraByProductId(id);
        return list;
    }

    @Override
    public List<ProductDetail> selectProductDetailsById(int id) {
        List<ProductDetail> list=new ArrayList<>();
        List<ProductDetail> fakelist=new ArrayList<>();

        list=productDAO.selectProductDetailsById(id);
        if(list==null){
            fakelist.add(new ProductDetail());
            return fakelist;
        }

        return list;
    }

    @Override
    public void saveEditedProductById(Product product) {
        productDAO.saveEditedProductById(product);
    }

    @Override
    public void UpdateProductImage(ProductImage image, Product product) {
         //if the original product does not have an extra image
        if(productDAO.selectExtraByProductId(product.getId())==null){
            productDAO.saveExtraImagesofProduct(image.getName(),product);
        }
        productDAO.UpdateProductImage(image,product);
    }

    @Override
    public void UpdateProductDetails(ProductDetail productDetail, Product product) {

        productDAO.UpdateProductDetails(productDetail,product);
    }

    @Override
    public void DeleteProductDetailsByProductId(int productId,int id) {
        productDAO.DeleteProductDetailsByProductId(productId,id);
    }

    @Override
    public List<Product> selectProductByPagination(int pageno) {
        List<Product> list=new ArrayList<>();
        list=productDAO.selectProductByPagination(pageno);
        if(list.size()>0){
            return list;
        }
        return null;
    }

    @Override
    public List<Integer> getPageCount() {
        List<PageNumber> list=new ArrayList<>();
        list=productDAO.getPageCount();
        List<Integer>Plist=new ArrayList<>();
        int pNumber=0;
        for(PageNumber page:list){
            pNumber=page.getPagenumber();
        }
        pNumber=(pNumber/10)+1;
        for(int i=1;i<pNumber;i++){
            Plist.add(i);
        }
        return Plist;
    }

    @Override
    public List<Product> getProductByCategoryId(int categoryid,int pageno) {
        List<Product>list=new ArrayList<>();
        list=productDAO.getProductByCategoryId(categoryid,pageno);
        return list;
    }


    @Override
    public Product findByNickName(String alias) {
        Product product=new Product();
        product=productDAO.findByNickName(alias);
        if(product!=null){
            return product;
        }
        return null;
    }

    @Override
    public List<Product> SearchByKeyword(int pageno, String search) {
        List<Product> list=productDAO.SearchByKeyword(pageno,search);

        return list;
    }

    @Override
    public List<Integer> getFilteredPageCount(String search) {
        List<PageNumber> list=new ArrayList<>();
        list=productDAO.getFilteredPageCount(search);
        List<Integer>Plist=new ArrayList<>();
        int pNumber=0;
        for(PageNumber page:list){
            pNumber=page.getPagenumber();
        }
        pNumber=(pNumber/10)+1;
        for(int i=1;i<pNumber;i++){
            Plist.add(i);
        }
        return Plist;
    }

    public List<Integer> getFilteredPageCount4Order(String search) {
        List<PageNumber> list=new ArrayList<>();
        list=productDAO.getFilteredPageCount(search);
        List<Integer>Plist=new ArrayList<>();
        int pNumber=0;
        for(PageNumber page:list){
            pNumber=page.getPagenumber();
        }
        pNumber=(pNumber/5)+1;
        for(int i=1;i<pNumber;i++){
            Plist.add(i);
        }
        return Plist;
    }

    @Override
    public List<Product> ProductSearchByKeywordforOrder(int pageno, String search) {
        List<Product> list=productDAO.ProductSearchByKeywordforOrder(pageno, search);
        return list;
    }

    @Override
    public Product findById(int id) {
        Product product=productDAO.findById(id);
        return product;
    }

    @Override
    public void UpdateReviewCountandAverageRating(int productId) {
        productDAO.UpdateReviewCountandAverageRating(productId);
    }

    @Override
    public List<Integer>  getPageCountForCategoriesWithParentId(int parentId) {
        List<PageNumber>list=productDAO.getPageCountForCategoriesWithParentId(parentId);
        int pageNumber=0;
        for(PageNumber DbPageNumber:list){
            pageNumber=DbPageNumber.getPagenumber();
        }
        pageNumber=(pageNumber/6)+1;
        List<Integer> pageList=new ArrayList<>();
        for(int i=0;i<pageNumber;i++){
            pageList.add(i);
        }

        return pageList;
    }
}
