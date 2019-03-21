package com.etrusted.interview.demo.service;

import javax.validation.ConstraintViolationException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.etrusted.interview.demo.entity.PaymentType;
import com.etrusted.interview.demo.rest.dto.OrderRequest;

@RunWith(SpringRunner.class)
@DataJpaTest
public class OrderServiceTest {

//  @Autowired
//  private OrderRepository orderRepository;

  @TestConfiguration
  static class OrderSerivceTestConfig {
    @Bean
    public OrderService orderService() {
      return new OrderService();
    }
  }

  @Autowired
  private OrderService orderService;

  @Before
  public void init() {
  }

  @Test
  public void happyPass() {
    OrderRequest orderRequest = new OrderRequest();
    orderRequest.setEmail("test@test.com");
    orderRequest.setOrderReference("orderRef1");
    orderRequest.setPaymentType(PaymentType.CREDIT_CARD.name());
    orderRequest.setShopURL("http://shopUrl");
    orderService.createOrder(orderRequest);
  }

  @Test
  public void validationError() {
    OrderRequest orderRequest = new OrderRequest();
    orderRequest.setOrderReference("orderRef1");
    orderRequest.setPaymentType(PaymentType.CREDIT_CARD.name());
    orderRequest.setShopURL("http://shopUrl");
    try {
      orderService.createOrder(orderRequest);
      Assert.fail("IllegalArgumentException is expected");
    } catch (IllegalArgumentException expected) {
      System.out.println("" + expected.getMessage());
    }
  }

  @Test
  public void addressValidation() {
    OrderRequest orderRequest = new OrderRequest();
    orderRequest.setEmail("test@test.com");
    orderRequest.setOrderReference("orderRef1");
    orderRequest.setPaymentType(PaymentType.CASH_ON_DELIVERY.name());
    orderRequest.setShopURL("http://shopUrl");
    try {
      orderService.createOrder(orderRequest);
      Assert.fail("IllegalArgumentException is expected");
    } catch (IllegalArgumentException expected) {
      Assert.assertTrue(expected.getMessage().contains("address"));
    }
  }

  @Test
  public void lengthConstraint() {
    OrderRequest orderRequest = new OrderRequest();
    orderRequest.setEmail(
        "test@test.com" + "01234567890123456789012345678901234567890123456789"
            + "01234567890123456789012345678901234567890123456789"
            + "01234567890123456789012345678901234567890123456789"
            + "01234567890123456789012345678901234567890123456789"
            + "01234567890123456789012345678901234567890123456789");
    orderRequest.setOrderReference("orderRef1");
    orderRequest.setPaymentType(PaymentType.CREDIT_CARD.name());
    orderRequest.setShopURL("http://shopUrl");
    try {
      orderService.createOrder(orderRequest);
      Assert.fail("ConstraintViolationException is expected");
    } catch (ConstraintViolationException expected) {
      Assert.assertNotNull(expected.getConstraintViolations().stream()
          .filter(p -> "email max length is 255".equals(p.getMessage()))
          .findFirst().orElse(null));

    }
  }
}
