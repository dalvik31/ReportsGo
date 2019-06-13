package com.epacheco.reports.Pojo.Client;

import com.epacheco.reports.Pojo.ClientDetail.ClientDetail;
import java.util.HashMap;

public class Client {
  private String id;
  private String Name;
  private String lastNanme;
  private String detail;
  private String phone;
  private double limit;
  private String dateClient;
  private HashMap<String, ClientDetail> ClientsDetails;

  public Client() {
    // Default constructor required for calls to DataSnapshot.getValue(User.class)
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return Name;
  }

  public void setName(String name) {
    Name = name;
  }

  public String getLastNanme() {
    return lastNanme;
  }

  public void setLastNanme(String lastNanme) {
    this.lastNanme = lastNanme;
  }

  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public double getLimit() {
    return limit;
  }

  public void setLimit(double limit) {
    this.limit = limit;
  }

  public String getDateClient() {
    return dateClient;
  }

  public void setDateClient(String dateClient) {
    this.dateClient = dateClient;
  }

  public HashMap<String, ClientDetail> getClientsDetails() {
    return ClientsDetails;
  }

  public void setClientsDetails(
      HashMap<String, ClientDetail> clientsDetails) {
    ClientsDetails = clientsDetails;
  }
}
