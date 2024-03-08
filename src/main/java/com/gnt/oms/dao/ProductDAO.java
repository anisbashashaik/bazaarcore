package com.gnt.oms.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;


import com.gnt.oms.entities.Product;
import com.gnt.oms.wrapper.ProductWrapper;

import jakarta.transaction.Transactional;

@Repository
public interface ProductDAO extends JpaRepository<Product, Integer> {
    
    List<ProductWrapper> getAllProducts(); 

    @Modifying
    @Transactional
    Integer updateStatus(@Param ("status") String status, @Param("productId") Integer productId);

    List<ProductWrapper> getProductByCategory(@Param ("categoryId") Integer categoryId);
    ProductWrapper getByProductId(@Param ("productId") Integer productId);
}
