package com.gnt.oms.service;

import java.util.List;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;

import com.gnt.oms.wrapper.ProductWrapper;

public interface ProductService {
    
    ResponseEntity<String> addProduct(Map<String, String> requestMap);
    ResponseEntity<List<ProductWrapper>> getAllProducts();
    ResponseEntity<String> updateProduct(Map<String, String> requestMap);
    ResponseEntity<String> deleteProduct(Integer productId);
    ResponseEntity<String> updateStatus(Map<String, String> requestMap);
    ResponseEntity<List<ProductWrapper>> getByCategory(Integer categoryId);
    ResponseEntity<ProductWrapper> getByProductId(Integer productId);

}
