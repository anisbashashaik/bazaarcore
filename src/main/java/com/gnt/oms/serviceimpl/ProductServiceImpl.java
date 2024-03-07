package com.gnt.oms.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gnt.oms.constants.OMSConstants;
import com.gnt.oms.dao.ProductDAO;
import com.gnt.oms.entities.Category;
import com.gnt.oms.entities.Product;
import com.gnt.oms.jwt.JwtFilter;
import com.gnt.oms.service.ProductService;
import com.gnt.oms.utils.OMSUtil;
import com.gnt.oms.wrapper.ProductWrapper;
import com.google.common.base.Strings;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductDAO productDAO;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addProduct(Map<String, String> requestMap) {
        try {
           if(jwtFilter.isAdmin()){
                if(validateProductMap(requestMap, false)){
                    productDAO.save(getProductFromMap(requestMap, false));
                    
                    
                    return OMSUtil.getResponseEntity("Product Saved Successfully", HttpStatus.OK);
                }
                return OMSUtil.getResponseEntity(OMSConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
           }else{
             return OMSUtil.getResponseEntity (OMSConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
           }
        } catch (Exception e) {
           e.printStackTrace();
        }
        return OMSUtil.getResponseEntity(OMSConstants.SOMETHING_WENT_STRING, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Product getProductFromMap(Map<String, String> requestMap, boolean isAdd) {
        Product product = new Product();
        Category category = new Category();
        category.setCategoryId(Integer.parseInt(requestMap.get("categoryId")));

        if(isAdd) {
            product.setProductId(Integer.parseInt(requestMap.get("productId")));
            product.setStatus("true");
        }else{
            product.setStatus("true");
        }
        product.setCategory(category);
        product.setProductName(requestMap.get("productName"));
        product.setDescription(requestMap.get("description"));
        product.setPrice(Integer.parseInt(requestMap.get("price")));
        return product;
    }

    private boolean validateProductMap(Map<String, String> requestMap, boolean validateId) {
        
        if(requestMap.containsKey("productName")){
            if(requestMap.containsKey("productId") && validateId){
                return true;
            }else if(!validateId){
                return true;
            }
        }
        return false;
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getAllProducts() {
        try {         
          return new ResponseEntity<>(productDAO.getAllProducts(), HttpStatus.OK);
       } catch (Exception e) {
        e.printStackTrace();
       }
       return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()){
                 if(validateProductMap(requestMap, true)){
                    Optional<Product> optional = productDAO.findById(Integer.parseInt(requestMap.get("productId")));
                    if(!optional.isEmpty()){
                        Product product = getProductFromMap(requestMap, true);
                        //product.setStatus(optional.get().getStatus());
                        productDAO.save(product);
                    }else{
                        return OMSUtil.getResponseEntity("Product Id not exists", HttpStatus.OK);
                    }
                    productDAO.save(getProductFromMap(requestMap, true));                    
                     return OMSUtil.getResponseEntity("Product Updated Successfully", HttpStatus.OK);
                 }else{
                    return OMSUtil.getResponseEntity(OMSConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
                 }
            }else{
              return OMSUtil.getResponseEntity (OMSConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
         } catch (Exception e) {
            e.printStackTrace();
         }
         return OMSUtil.getResponseEntity(OMSConstants.SOMETHING_WENT_STRING, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}
