package com.gnt.oms.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.gnt.oms.entities.Category;

public interface CategoryService {
    
    ResponseEntity<String> addNewCategory(Map<String, String> requestMapping);
    ResponseEntity<List<Category>> getAllCategories(String filterValue);
    ResponseEntity<String> updateCategory(Map<String, String> requestMapping);
}
