/**
 * Project: mars-backend-challenge-template
 *
 * <p>Copyright (c) 2018 Trusted Shops GmbH All rights reserved.
 */
package com.etrusted.interview.demo.repository;

import com.etrusted.interview.demo.entity.Order;
import com.etrusted.interview.demo.entity.Shop;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * OrderRepository
 *
 * @author created by trumga2 8 Sep 2018 10:52:18
 */
@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
  @Query(name = "FROM order o where o.shop=?1", nativeQuery = false)
  List<Order> findByShop(Shop shop);
}
