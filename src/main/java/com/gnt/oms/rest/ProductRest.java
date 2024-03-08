package com.gnt.oms.rest;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.gnt.oms.wrapper.ProductWrapper;

@RequestMapping(path = "/product")
public interface ProductRest {
    
    @PostMapping(path ="/add")
    ResponseEntity<String> addProduct(@RequestBody(required = true) Map<String, String> requestMap);

    @GetMapping(path = "/get")
    public ResponseEntity<List<ProductWrapper>> getAllProducts();

    @PostMapping(path = "/update")
    ResponseEntity<String> updateProduct(@RequestBody Map<String, String> requestMap);

    @DeleteMapping("/delete/{productId}")
    ResponseEntity<String> deleteProduct(@PathVariable Integer productId);

    @PostMapping(path = "/updateStatus")
    ResponseEntity<String> updateStatus(@RequestBody Map<String, String> requestMap);

    @GetMapping(path = "/getByCategory/{categoryId}")
    public ResponseEntity<List<ProductWrapper>> getByCategory(@PathVariable Integer categoryId);

    @GetMapping(path = "/getByProductId/{productId}")
    public ResponseEntity<ProductWrapper> getByProductId(@PathVariable Integer productId);
}
