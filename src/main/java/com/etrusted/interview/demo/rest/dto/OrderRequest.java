package com.etrusted.interview.demo.rest.dto;

public class OrderRequest {
  private String shopURL;
  private String orderReference;
  private String firstName;
  private String lastName;
  private String email;

  public String getShopURL() {
    return shopURL;
  }

  public void setShopURL(String shopURL) {
    this.shopURL = shopURL;
  }

  public String getOrderReference() {
    return orderReference;
  }

  public void setOrderReference(String orderReference) {
    this.orderReference = orderReference;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
