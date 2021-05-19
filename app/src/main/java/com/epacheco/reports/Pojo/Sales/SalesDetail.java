package com.epacheco.reports.Pojo.Sales;

public class SalesDetail {
    private String saleId;
    private String imgProduct;
    private double productPricreBuy;
    private double productPriceSale;
    private String productName;
    private String productId;
    private String idClient;
    private String nameClient;
    private int auxStock;

    public String getImgProduct() {
        return imgProduct;
    }

    public void setImgProduct(String imgProduct) {
        this.imgProduct = imgProduct;
    }

    public double getProductPricreBuy() {
        return productPricreBuy;
    }

    public void setProductPricreBuy(double productPricreBuy) {
        this.productPricreBuy = productPricreBuy;
    }

    public double getProductPriceSale() {
        return productPriceSale;
    }

    public void setProductPriceSale(double productPriceSale) {
        this.productPriceSale = productPriceSale;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public String getNameClient() {
        return nameClient;
    }

    public void setNameClient(String nameClient) {
        this.nameClient = nameClient;
    }

    public String getSaleId() {
        return saleId;
    }

    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }

    public int getAuxStock() {
        return auxStock;
    }

    public void setAuxStock(int auxStock) {
        this.auxStock = auxStock;
    }
}
