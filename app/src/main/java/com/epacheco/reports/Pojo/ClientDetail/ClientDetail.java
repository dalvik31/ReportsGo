package com.epacheco.reports.Pojo.ClientDetail;


import com.epacheco.reports.R;
import com.epacheco.reports.Tools.ReportsApplication;
import com.epacheco.reports.Tools.Tools;

public class ClientDetail {

  private String datePayment;
  private double amount;
  private String concept;
  private boolean isPay;
  private double debt;
  private int cantProduct;
  private String urlImage;
  private String productId;
  private int updateStock;
  public ClientDetail() {
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public String getConcept() {
    return concept;
  }

  public void setConcept(String concept) {
    this.concept = concept;
  }

  public boolean isPay() {
    return isPay;
  }

  public void setPay(boolean pay) {
    isPay = pay;
  }

  public double getDebt() {
    return debt;
  }

  public void setDebt(double debt) {
    this.debt = debt;
  }

  public String getDatePayment() {
    return datePayment;
  }

  public void setDatePayment(String datePayment) {
    this.datePayment = datePayment;
  }

  public int getCantProduct() {
    return cantProduct;
  }

  public void setCantProduct(int cantProduct) {
    this.cantProduct = cantProduct;
  }

  public String getUrlImage() {
    return urlImage;
  }

  public void setUrlImage(String urlImage) {
    this.urlImage = urlImage;
  }

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public int getUpdateStock() {
    return updateStock;
  }

  public void setUpdateStock(int updateStock) {
    this.updateStock = updateStock;
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("\n");
    stringBuilder.append("\n");
    stringBuilder.append("\n");
    stringBuilder.append("** ");
    stringBuilder.append("Fecha del movimiento: ");
    stringBuilder.append("\n");
    stringBuilder.append(Tools.getFormatDateHour(datePayment));
    stringBuilder.append(" **");
    stringBuilder.append("\n");
    stringBuilder.append("Cantidad de pago: ");
    stringBuilder.append("\n");
    stringBuilder.append(amount);
    stringBuilder.append("\n");
    stringBuilder.append("Cantidad de productos: ");
    stringBuilder.append("\n");
    stringBuilder.append(cantProduct);
    stringBuilder.append("\n");
    stringBuilder.append("Concepto de pago: ");
    stringBuilder.append("\n");
    stringBuilder.append(concept!=null && !concept.isEmpty() ? concept: ReportsApplication.getMyApplicationContext().getString(R.string.txt_client_without_concept_payment));
    stringBuilder.append("\n");
    stringBuilder.append("*********************************************");
    stringBuilder.append("\n");
    stringBuilder.append("\n");
    stringBuilder.append("\n");
    return stringBuilder.toString();
  }
}

