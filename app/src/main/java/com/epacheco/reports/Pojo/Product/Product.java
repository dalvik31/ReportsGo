package com.epacheco.reports.Pojo.Product;

public class Product {
  private String productDate;
  private String productId;
  private String urlImage;
  private String productName;
  private String productDescription;
  private String productType;
  private double productPriceBuy;
  private double productPriceSale;
  private String productCode;
  private int inStock;
  private double auxPrice;
  private int auxStock;

  public Product() {
  }

  public String getUrlImage() {
    return urlImage;
  }

  public void setUrlImage(String urlImage) {
    this.urlImage = urlImage;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getProductDescription() {
    return productDescription;
  }

  public void setProductDescription(String productDescription) {
    this.productDescription = productDescription;
  }

  public String getProductType() {
    return productType;
  }

  public void setProductType(String productType) {
    this.productType = productType;
  }

  public double getProductPriceBuy() {
    return productPriceBuy;
  }

  public void setProductPriceBuy(double productPriceBuy) {
    this.productPriceBuy = productPriceBuy;
  }

  public double getProductPriceSale() {
    return productPriceSale;
  }

  public void setProductPriceSale(double productPriceSale) {
    this.productPriceSale = productPriceSale;
  }

  public String getProductCode() {
    return productCode;
  }

  public void setProductCode(String productCode) {
    this.productCode = productCode;
  }

  public String getProductDate() {
    return productDate;
  }

  public void setProductDate(String productDate) {
    this.productDate = productDate;
  }

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public int getInStock() {
    return inStock;
  }

  public void setInStock(int inStock) {
    this.inStock = inStock;
  }


  public double getAuxPrice() {
    return auxPrice;
  }

  public void setAuxPrice(double auxPrice) {
    this.auxPrice = auxPrice;
  }

  public int getAuxStock() {
    return auxStock;
  }

  public void setAuxStock(int auxStock) {
    this.auxStock = auxStock;
  }

  @Override
  public boolean equals( Object obj) {
    Product myProduct = (Product) obj;
    return myProduct.getProductId().equals(getProductId());
    /*return myProduct.getProductDate().equals(getProductDate())
        && myProduct.getProductId().equals(getProductId())
        && myProduct.getUrlImage().equals(getUrlImage())
        && myProduct.getProductName().equals(getProductName())
        && myProduct.getProductDescription().equals(getProductDescription())
        && myProduct.getProductType().equals(getProductType())
        && myProduct.getProductPriceBuy() ==getProductPriceBuy()
        && myProduct.getProductPriceSale()== getProductPriceSale()
        && myProduct.getProductCode() != getProductCode()
        && myProduct.getInStock() == getInStock()
        &&myProduct.getAuxPrice() == getAuxPrice()
        && myProduct.getAuxStock() == getAuxStock();*/
   /* return !myProduct.getProductDate().equals(getProductDate())
        && !myProduct.getProductId().equals(getProductId())
        && !myProduct.getUrlImage().equals(getUrlImage())
        && !myProduct.getProductName().equals(getProductName())
        && !myProduct.getProductDescription().equals(getProductDescription())
        && !myProduct.getProductType().equals(getProductType())
        && myProduct.getProductPriceBuy() !=getProductPriceBuy()
        && myProduct.getProductPriceSale()!= getProductPriceSale()
        && myProduct.getProductCode() != getProductCode()
        && myProduct.getInStock() != getInStock()
        &&myProduct.getAuxPrice() != getAuxPrice()
        && myProduct.getAuxStock() != getAuxStock();*/
  }
}
