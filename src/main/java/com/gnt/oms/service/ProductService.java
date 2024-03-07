package com.gnt.oms.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.gnt.oms.wrapper.ProductWrapper;

public interface ProductService {
    
    ResponseEntity<String> addProduct(Map<String, String> requestMap);
    ResponseEntity<List<ProductWrapper>> getAllProducts();
    ResponseEntity<String> updateProduct(Map<String, String> requestMap);

}
