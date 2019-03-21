package com.etrusted.interview.demo.rest.dto;

public class OrderRequest {
  private String shopURL;
  private String orderReference;
  private String firstName;
  private String lastName;
  private String email;
  private String paymentType;
  private String address;

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

  public String getPaymentType() {
    return paymentType;
  }

  public void setPaymentType(String paymentType) {
    this.paymentType = paymentType;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }
}
