
package com.gnt.oms.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gnt.oms.constants.OMSConstants;
import com.gnt.oms.dao.CategoryDAO;
import com.gnt.oms.entities.Category;
import com.gnt.oms.jwt.JwtFilter;
import com.gnt.oms.service.CategoryService;
import com.gnt.oms.utils.OMSUtil;
import java.util.Optional;
import com.google.common.base.Strings;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryDAO categoryDAO;
        
    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addNewCategory(Map<String, String> requestMapping) {
        try {

            if (jwtFilter.isAdmin()) {

                if (validateCategoryMap(requestMapping, false)) {
                    categoryDAO.save(getCategoryFromMap(requestMapping, false));
                    return OMSUtil.getResponseEntity("Category added Successfully", HttpStatus.OK);
                }                
            } else {
                return OMSUtil.getResponseEntity(OMSConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return OMSUtil.getResponseEntity(OMSConstants.SOMETHING_WENT_STRING, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateCategoryMap(Map<String, String> requestMapping, boolean validateId) {
        
        if(requestMapping.containsKey("categoryName")){
            if(requestMapping.containsKey("categoryId") && validateId){
                return true;
            }else if(!validateId){
                return true;
            }
        }
        return false;
    }

    private Category getCategoryFromMap(Map<String, String> requestMapping, Boolean isAdd){

       Category category = new Category();
        if(isAdd){
            category.setCategoryId(Integer.parseInt(requestMapping.get("categoryId")));
        }
        category.setCategoryName(requestMapping.get("categoryName"));
        return category;

    }

    @Override
    public ResponseEntity<List<Category>> getAllCategories(String filterValue) {
       try {
          if(!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true")){
            return new ResponseEntity<List<Category>>(categoryDAO.getAllCategory(), HttpStatus.OK);
          }
          return new ResponseEntity<>(categoryDAO.findAll(), HttpStatus.OK);
       } catch (Exception e) {
        e.printStackTrace();
       }
       return new ResponseEntity<List<Category>>(new ArrayList(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> requestMapping) {
        try {

            if (jwtFilter.isAdmin()) {

                if (validateCategoryMap(requestMapping, true)) {
                    Optional optional = categoryDAO.findById(Integer.parseInt(requestMapping.get("categoryId")));
                    if(!optional.isEmpty()) {
                        categoryDAO.save(getCategoryFromMap(requestMapping, true));
                        return OMSUtil.getResponseEntity("Category Updated Successfully", HttpStatus.OK);
                    }else{
                        return OMSUtil.getResponseEntity("Category Id does not exists", HttpStatus.OK);
                    }                    
                }
                return OMSUtil.getResponseEntity(OMSConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);                
            } else {
                return OMSUtil.getResponseEntity(OMSConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return OMSUtil.getResponseEntity(OMSConstants.SOMETHING_WENT_STRING, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}