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

@NamedQuery (name = "Bill.getAllBills", query = "select b from Bill b order by b.billId desc") 
@NamedQuery (name = "Bill.getBillByUserName", query = "select b from Bill b where b.createdBy=:createdBy order by b.billId desc")

@Data
@Entity 
@DynamicInsert
@DynamicUpdate
@Table(name="bill")
public class Bill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_id")
    private Integer billId;
    
    @Column(name = "uuid")
    private String uuid;

    @Column(name = "bill_Name")
    private String billName;

    @Column(name = "email")
    private String email;

    @Column(name = "contact_no")
    private String contactNo;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "total")
    private Integer total;

    @Column(name = "product_details", columnDefinition = "json")
    private String productDetails;

    @Column(name = "created_by")
    private String createdBy;

}
