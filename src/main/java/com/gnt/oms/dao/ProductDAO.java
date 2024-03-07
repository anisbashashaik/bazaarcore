package com.gnt.oms.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.gnt.oms.entities.Product;
import com.gnt.oms.wrapper.ProductWrapper;

@Repository
public interface ProductDAO extends JpaRepository<Product, Integer> {
    
    List<ProductWrapper> getAllProducts(); 
}
