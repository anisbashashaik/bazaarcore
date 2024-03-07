package com.gnt.oms.restimpl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.gnt.oms.constants.OMSConstants;
import com.gnt.oms.entities.Category;
import com.gnt.oms.rest.CategoryRest;
import com.gnt.oms.service.CategoryService;
import com.gnt.oms.utils.OMSUtil;

@RestController
public class CategoryRestImpl implements CategoryRest{

    @Autowired
    CategoryService categoryService;

    @Override
    public ResponseEntity<String> addNewCategory(Map<String, String> requestMapping) {
       try{
           return categoryService.addNewCategory(requestMapping);
        }catch (Exception e) {
            e.printStackTrace();
        }

       return OMSUtil.getResponseEntity(OMSConstants.SOMETHING_WENT_STRING, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Category>> getAllCategories(String filterValue) {
        try{
            return categoryService.getAllCategories(filterValue);
         }catch (Exception e) {
             e.printStackTrace();
         }
 
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> requestMapping) {
        try{
            return categoryService.updateCategory(requestMapping);
         }catch (Exception e) {
             e.printStackTrace();
         }
 
        return OMSUtil.getResponseEntity(OMSConstants.SOMETHING_WENT_STRING, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
