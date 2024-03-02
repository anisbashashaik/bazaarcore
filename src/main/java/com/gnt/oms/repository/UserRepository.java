package com.gnt.oms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gnt.oms.entities.YFSUser;

@Repository
public interface UserRepository extends JpaRepository<YFSUser, Long>{
    

   YFSUser findFirstByEmailId(String emailId);

   YFSUser findByRole(String role);

}
