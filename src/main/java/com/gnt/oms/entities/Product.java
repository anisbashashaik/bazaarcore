package com.gnt.oms.entities;

import java.io.Serializable;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.Data;

@NamedQuery (name = "Product.getAllProducts", query = "select new com.gnt.oms.wrapper.ProductWrapper(p.productId, p.productName, p.description, p.price, p.status, p.category.categoryId, p.category.categoryName) from Product p") 
@NamedQuery (name = "Product.updateStatus", query = "update Product p set p.status=:status where p.productId=:productId")
@NamedQuery (name = "Product.getProductByCategory", query = "select new  com.gnt.oms.wrapper.ProductWrapper(p.productId, p.productName) from Product p where p.category.categoryId=:categoryId and p.status='true'")
@NamedQuery(name ="Product.getByProductId", query = "select new  com.gnt.oms.wrapper.ProductWrapper(p.productId, p.productName, p.description, p.price) from Product p where p.productId=:productId")

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "yfs_product")
public class Product implements Serializable{

    public static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "product_name")
    private String productName;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "category_fk", nullable = false)
    private Category category;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Integer price;

    @Column(name = "status")
    private String status;

}
