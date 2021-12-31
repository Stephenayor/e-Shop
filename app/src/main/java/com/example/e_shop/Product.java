package com.example.e_shop;

public class Product {
    private String productImage;
    private String productTitle;
    private String productPrice;

    public Product(String productImage, String productTitle, String productPrice) {
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.productPrice = productPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }
}
