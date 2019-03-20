/**
 * Project: interview-backend-robert
 *
 * <p>Copyright (c) 2018 Trusted Shops GmbH All rights reserved.
 */
package com.etrusted.interview.demo.rest;

import com.etrusted.interview.demo.rest.dto.Alive;
import com.etrusted.interview.demo.rest.dto.OrderRequest;
import com.etrusted.interview.demo.rest.dto.OrderResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * OrderRest
 *
 * @author created by trumga2 27 Mar 2018 11:21:15
 */
@RestController
public class OrderRest {

  public ResponseEntity<OrderResponse> createOrder(OrderRequest orderRequest) {
    return null;
  }

  @RequestMapping(value = "/alive", method = RequestMethod.GET)
  public ResponseEntity<Alive> alive() {
    return new ResponseEntity<>(new Alive(true), HttpStatus.OK);
  }
}
