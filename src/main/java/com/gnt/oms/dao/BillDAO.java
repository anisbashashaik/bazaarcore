package com.gnt.oms.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gnt.oms.entities.Bill;

@Repository
public interface BillDAO extends JpaRepository<Bill, Integer> {
    
    List<Bill> getAllBills();
    List<Bill> getBillByUserName(@Param("createdBy") String createdBy);
}
