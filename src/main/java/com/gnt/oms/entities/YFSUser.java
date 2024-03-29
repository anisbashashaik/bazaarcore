package com.gnt.oms.entities;

import java.io.Serializable;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.Data;

@NamedQuery (name="YFSUser.findByEmailId", query = "select u from YFSUser u where u.emailId=:emailId")
@NamedQuery (name = "YFSUser.getAllUsers", query = "select new com.gnt.oms.wrapper.UserWrapper(u.userId, u.userName, u.emailId, u.contactNo, u.status) from YFSUser u where u.role='User'") 
@NamedQuery (name = "YFSUser.updateStatus", query = "update YFSUser u set u.status=:status where u.userId=:userId")
@NamedQuery (name = "YFSUser.getAllAdmin", query = "select u.emailId from YFSUser u where u.role='Admin'") 


@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "yfs_user")
public class YFSUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "contact_no")
    private String contactNo;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private String status;

    @Column(name = "role")
    private String role;

}
