/**
 * Project: interview-backend-robert
 *
 * <p>Copyright (c) 2018 Trusted Shops GmbH All rights reserved.
 */
package com.etrusted.interview.demo.rest;

import com.etrusted.interview.demo.entity.Order;
import com.etrusted.interview.demo.rest.dto.Alive;
import com.etrusted.interview.demo.rest.dto.OrderRequest;
import com.etrusted.interview.demo.rest.dto.OrderResponse;
import com.etrusted.interview.demo.service.OrderService;
import com.etrusted.interview.demo.service.ParameterUtils;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * OrderRest
 *
 * @author created by trumga2 27 Mar 2018 11:21:15
 */
@RestController
public class OrderRest {

  @Autowired
  private OrderService orderService;

  /**
   * Create order.
   *
   * @param orderRequest order data
   * @return order id
   */
  @RequestMapping(value = "/api/orders", method = RequestMethod.POST)
  public ResponseEntity<OrderResponse> createOrder(
      @RequestBody OrderRequest orderRequest) {
    try {
      return new ResponseEntity<>(orderService.createOrder(orderRequest),
          HttpStatus.OK);
    } catch (IllegalArgumentException | ConstraintViolationException error) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          error.getMessage());
    }
  }

  /**
   * Get orders by shop.
   *
   * @param shopId shop id
   * @param offsetParam offset
   * @param limitParam page size
   * @return orders
   */
  @RequestMapping(value = "/api/shops/{id}/orders", method = RequestMethod.GET)
  public List<Order> getOrdersByShop(
      @PathVariable(name = "id", required = true) String shopId,
      @RequestParam(name = "offset", required = false) String offsetParam,
      @RequestParam(name = "limit", required = false) String limitParam) {
    Long id = ParameterUtils.parseLongDefault(shopId, null);
    if (id == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "shop id is required");
    }
    int offset = ParameterUtils.parseIntDefault(offsetParam, 0);
    int limit = ParameterUtils.parseIntDefault(offsetParam, 10);
    return orderService.getOrdersByShop(id, limit, offset);
  }

  @RequestMapping(value = "/alive", method = RequestMethod.GET)
  public ResponseEntity<Alive> alive() {
    return new ResponseEntity<>(new Alive(true), HttpStatus.OK);
  }
}
