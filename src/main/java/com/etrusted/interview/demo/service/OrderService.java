package com.etrusted.interview.demo.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import javax.validation.ConstraintViolationException;

import java.util.LinkedList;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etrusted.interview.demo.entity.Order;
import com.etrusted.interview.demo.entity.PaymentType;
import com.etrusted.interview.demo.entity.Shop;
import com.etrusted.interview.demo.entity.User;
import com.etrusted.interview.demo.repository.OrderRepository;
import com.etrusted.interview.demo.repository.ShopRepository;
import com.etrusted.interview.demo.repository.UserRepository;
import com.etrusted.interview.demo.rest.dto.OrderRequest;
import com.etrusted.interview.demo.rest.dto.OrderResponse;

@Service
public class OrderService {
  
  @Autowired
  private UserRepository userRepository;
  
  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private ShopRepository shopRepository;
  
  /**
   * Create an order based on order request.
   *
   * {@link OrderRequest} contains some information about order reference, user and shop.
   * <ol>
   * <li>Validates input data: shop URL, order reference, payment type, and user email must not be empty.</li>
   * <li>Retrieves or create shop and user based on order request data</li>
   * <li>Does validation for &quot;cash on delivery&quot; case</li>
   * <li>Create an order</li>
   * </ol>
   * Throws {@link IllegalArgumentException} and {@link ConstraintViolationException} if validation failed.
   *  
   * @param orderRequest order request
   * @return order response
   */
  @Transactional(value=TxType.REQUIRED)
  public OrderResponse createOrder(OrderRequest orderRequest) {
    // validate input data
    validate(orderRequest);
    
    // find/create user and shop
    User user = findOrCreateUser(orderRequest);
    Shop shop = findOrCreateShop(orderRequest.getShopURL());

    // validate "cash on delivery"
    // TODO extract method
    if (PaymentType.CASH_ON_DELIVERY.equals(PaymentType.valueOf(orderRequest.getPaymentType()))) {
      if (Strings.isBlank(user.getAddress())) {
        throw new IllegalArgumentException("address can't be found!");
      }
    }

    // create an order
    Order order = new Order();
    order.setOrderReference(orderRequest.getOrderReference());
    order.setPaymentType(PaymentType.valueOf(orderRequest.getPaymentType()));
    order.setShop(shop);
    order.setUser(user);
    order = orderRepository.save(order);
    OrderResponse orderResponse = new OrderResponse();
    orderResponse.setId(order.getId());
    return orderResponse;
  }
  
  private User findOrCreateUser(OrderRequest orderRequest) {
    String email = orderRequest.getEmail().toLowerCase();
    Optional<User> userOpt = userRepository.findByEmail(email);
    if (userOpt.isPresent()) { // found?
      User user = userOpt.get();
      // update user if necessary
      // TODO other fields
      boolean update = false;
      String address = orderRequest.getAddress();
      if (!(Strings.isBlank(address) || address.equalsIgnoreCase(user.getAddress()))) {
        user.setAddress(address);
      }
      if (update) {
        return userRepository.save(user);
      }
      return user;
    } else {
      // create a new one
      User user = new User();
      user.setEmail(email);
      user.setAddress(orderRequest.getAddress());
      user.setFirstName(orderRequest.getFirstName());
      user.setLastName(orderRequest.getLastName());
      return userRepository.save(user);
    }
  }
  
  private Shop findOrCreateShop(String shopUrl) {
    Optional<Shop> shopOpt = shopRepository.findByUrl(shopUrl);
    if (shopOpt.isPresent()) { // found?
      return shopOpt.get();
    } else {
      // create a new one
      Shop shop = new Shop();
      shop.setUrl(shopUrl);
      return shopRepository.save(shop);
    }
  }
  

  private void validate(OrderRequest orderRequest) {
    List<String> errors = new LinkedList<>();
    if (Strings.isBlank(orderRequest.getShopURL())) {
      errors.add("Shop URL must not be empty");
    }
    if (Strings.isBlank(orderRequest.getEmail())) {
      errors.add("email must not be empty");
    }
    if (Strings.isBlank(orderRequest.getOrderReference())) {
      errors.add("order reference must not be empty");
    }
    try {
       PaymentType.valueOf(orderRequest.getPaymentType());
    } catch (IllegalArgumentException | NullPointerException fatal) {
      errors.add("wrong payment type");
    }
    if (!errors.isEmpty()) {
      StringBuilder b = new StringBuilder("Validation errors:");
      for (String error : errors) {
        b.append(" ");
        b.append(error);
        b.append(";");
      }
      throw new IllegalArgumentException(b.toString());
    }
  }
  
  
}
