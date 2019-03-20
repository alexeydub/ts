package com.etrusted.interview.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = "order")
@Table(name = "shop_order")
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "order_reference")
  private String orderReference;

  @ManyToOne
  @JoinColumn(name = "user_ref")
  private User user;

  @ManyToOne
  @JoinColumn(name = "shop_ref")
  private Shop shop;

  @Column(name = "payment_type")
  @Enumerated(EnumType.STRING)
  private PaymentType paymentType;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getOrderReference() {
    return orderReference;
  }

  public void setOrderReference(String reference) {
    this.orderReference = reference;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Shop getShop() {
    return shop;
  }

  public void setShop(Shop shop) {
    this.shop = shop;
  }

  public PaymentType getPaymentType() {
    return paymentType;
  }

  public void setPaymentType(PaymentType paymentType) {
    this.paymentType = paymentType;
  }
}
