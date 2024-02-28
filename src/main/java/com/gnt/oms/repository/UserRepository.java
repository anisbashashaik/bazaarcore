package com.gnt.oms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gnt.oms.entities.User;
import com.gnt.oms.enums.UserRole;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    

   User findFirstByEmail(String email);

   User findByUserRole(UserRole role);

}
