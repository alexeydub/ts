package com.etrusted.interview.demo.service;

import java.util.List;

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

import com.etrusted.interview.demo.entity.Order;
import com.etrusted.interview.demo.entity.PaymentType;
import com.etrusted.interview.demo.entity.Shop;
import com.etrusted.interview.demo.entity.User;
import com.etrusted.interview.demo.repository.OrderRepository;
import com.etrusted.interview.demo.repository.ShopRepository;
import com.etrusted.interview.demo.repository.UserRepository;
import com.etrusted.interview.demo.rest.dto.OrderRequest;

@RunWith(SpringRunner.class)
@DataJpaTest
public class OrderServiceTest {

  @Autowired
  private OrderRepository orderRepository;
  
  @Autowired
  private ShopRepository shopRepository;
  
  @Autowired
  private UserRepository userRepository;

  @TestConfiguration
  static class OrderSerivceTestConfig {
    @Bean
    public OrderService orderService() {
      return new OrderService();
    }
  }

  @Autowired
  private OrderService orderService;

  private Long shopWithOrders = null;
  private Long userWithOrders = null;
  
  @Before
  public void init() {
    User user = new User();
    user.setAddress("address1");
    user.setEmail("user1@test.com");
    user.setFirstName("First");
    user.setLastName("Name1");
    user = userRepository.save(user);
    userWithOrders = user.getId();
    
    Shop shop = new Shop();
    shop.setUrl("shop1");
    shop = shopRepository.save(shop);
    shopWithOrders = shop.getId();
    
    Order order = new Order();
    order.setOrderReference("ref1");
    order.setPaymentType(PaymentType.PAYPAL);
    order.setShop(shop);
    order.setUser(user);
    orderRepository.saveAndFlush(order);
    
    order = new Order();
    order.setOrderReference("ref2");
    order.setPaymentType(PaymentType.CREDIT_CARD);
    order.setShop(shop);
    order.setUser(user);
    orderRepository.saveAndFlush(order);
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
  
  @Test
  public void retriveOrdersByShop() {
    List<Order> orders = orderService.getOrdersByShop(1L, 10, 0);
    Assert.assertEquals(1, orders.size());
    Order order = orders.get(0);
    Assert.assertEquals(new Long(1L), order.getShop().getId());
    Assert.assertEquals("john.doe@example.com", order.getUser().getEmail());
    
    orders = orderService.getOrdersByShop(shopWithOrders, 10, 0);
    Assert.assertEquals(2, orders.size());
    order = orders.get(0);
    Assert.assertEquals(shopWithOrders, order.getShop().getId());
    Assert.assertEquals(userWithOrders, order.getUser().getId());
    
  }
}
