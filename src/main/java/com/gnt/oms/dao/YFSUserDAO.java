package com.gnt.oms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import com.gnt.oms.entities.YFSUser;
import com.gnt.oms.wrapper.UserWrapper;

import jakarta.transaction.Transactional;

import java.util.List;

public interface YFSUserDAO extends JpaRepository<YFSUser, Integer> {
    
    YFSUser findByEmailId(@Param("emailId") String emailId);
    List<UserWrapper> getAllUsers();
    @Transactional
    @Modifying
    Integer updateStatus(@Param("status") String status, @Param("userId") String userId);
    List<String> getAllAdmin();
}
