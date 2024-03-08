package com.gnt.oms.restimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.gnt.oms.constants.OMSConstants;
import com.gnt.oms.entities.Bill;
import com.gnt.oms.rest.BillRest;
import com.gnt.oms.service.BillService;
import com.gnt.oms.utils.OMSUtil;

@RestController
public class BillRestImpl implements BillRest{

    @Autowired BillService billService;

    @Override
    public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {
      try{
            return billService.generateReport(requestMap);
         }catch (Exception e) {
             e.printStackTrace();
         }
 
       return OMSUtil.getResponseEntity(OMSConstants.SOMETHING_WENT_STRING, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Bill>> getBills() {
      try{
        return billService.getBills();
     }catch (Exception e) {
         e.printStackTrace();
     }

     return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap) {
      try{
        return billService.getPdf(requestMap);
     }catch (Exception e) {
         e.printStackTrace();
     }
      return null;
      //return new ResponseEntity<byte[]>(new byte[], HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteBill(Integer billId) {
      try {
          return billService.deleteBill(billId);
      } catch (Exception e) {
        e.printStackTrace();
      }
      return OMSUtil.getResponseEntity(OMSConstants.SOMETHING_WENT_STRING, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    
}
