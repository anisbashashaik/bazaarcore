package com.gnt.oms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.gnt.oms.entities.YFSUser;

public interface YFSUserDAO extends JpaRepository<YFSUser, Integer> {
    
    YFSUser findByEmailId(@Param("emailId") String emailId);
}
