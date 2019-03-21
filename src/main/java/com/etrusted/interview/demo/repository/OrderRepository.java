/**
 * Project: mars-backend-challenge-template
 *
 * <p>Copyright (c) 2018 Trusted Shops GmbH All rights reserved.
 */
package com.etrusted.interview.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.etrusted.interview.demo.entity.Order;
import com.etrusted.interview.demo.entity.Shop;

/**
 * OrderRepository
 *
 * @author created by trumga2 8 Sep 2018 10:52:18
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
  @Query(name = "FROM order o where o.shop=?1", nativeQuery = false)
  List<Order> findByShop(Shop shop);
}
