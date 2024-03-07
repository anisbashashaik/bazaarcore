package com.gnt.oms.restimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.gnt.oms.constants.OMSConstants;
import com.gnt.oms.entities.Product;
import com.gnt.oms.rest.ProductRest;
import com.gnt.oms.service.ProductService;
import com.gnt.oms.utils.OMSUtil;
import com.gnt.oms.wrapper.ProductWrapper;

@RestController
public class ProductRestImpl implements ProductRest{

    @Autowired
    ProductService productService;

    @Override
    public ResponseEntity<String> addProduct(Map<String, String> requestMap) {
       try {
         return productService.addProduct(requestMap);
       } catch (Exception e) {
            e.printStackTrace();
       }
       return OMSUtil.getResponseEntity(OMSConstants.SOMETHING_WENT_STRING, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getAllProducts() {
         try{
            return productService.getAllProducts();
         }catch (Exception e) {
             e.printStackTrace();
         }
 
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
        try {
            return productService.updateProduct(requestMap);
          } catch (Exception e) {
               e.printStackTrace();
          }
          return OMSUtil.getResponseEntity(OMSConstants.SOMETHING_WENT_STRING, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}
