package com.gnt.oms.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gnt.oms.entities.Category;

@Repository
public interface CategoryDAO extends JpaRepository<Category, Integer>{
    List<Category> getAllCategory();   
}
