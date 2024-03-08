package com.gnt.oms.serviceimpl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gnt.oms.dao.BillDAO;
import com.gnt.oms.dao.CategoryDAO;
import com.gnt.oms.dao.ProductDAO;
import com.gnt.oms.service.DashboardService;


@Service
public class DashboardServiceImpl implements DashboardService{

    @Autowired
    CategoryDAO categoryDAO;

    @Autowired
    ProductDAO productDAO;

    @Autowired
    BillDAO billDAO;
    
    @Override
    public ResponseEntity<Map<String, Object>> getCount() {
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("category", categoryDAO.count());
        map.put("product", productDAO.count());
        map.put("bill", billDAO.count());
        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
    }
    
}
