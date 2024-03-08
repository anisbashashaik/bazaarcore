package com.gnt.oms.wrapper;

import lombok.Data;

@Data
public class ProductWrapper {

    Integer productId;
    String productName;
    String description;
    Integer price;
    String status;
    Integer categoryId;
    String categoryName;

    public ProductWrapper() {

    }

    ProductWrapper(Integer productId, String productName, String description, Integer price, String status,
            Integer categoryId, String categoryName) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.status = status;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    ProductWrapper(Integer productId, String productName, String description, Integer price) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.price = price;
    }

    ProductWrapper(Integer productId, String productName) {
        this.productId = productId;
        this.productName = productName;
    }
}
