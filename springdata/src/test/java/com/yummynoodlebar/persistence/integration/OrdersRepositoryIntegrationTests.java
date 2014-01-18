package com.yummynoodlebar.persistence.integration;


import com.yummynoodlebar.config.JPAConfiguration;
import com.yummynoodlebar.persistence.domain.Order;
import com.yummynoodlebar.persistence.repository.OrdersRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JPAConfiguration.class})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class OrdersRepositoryIntegrationTests {

  @Autowired
  OrdersRepository ordersRepository;

  @Test
  public void thatItemIsInsertedIntoRepoWorks() throws Exception {
    String key = UUID.randomUUID().toString();

    Order order = new Order();
    order.setDateTimeOfSubmission(new Date());
    order.setId(key);

    Map<String, Integer> items = new HashMap<String, Integer>();

    items.put("yummy1", 15);
    items.put("yummy3", 12);
    items.put("yummy5", 7);

    order.setOrderItems(items);

    ordersRepository.save(order);

    Order retrievedOrder = ordersRepository.findById(key);

    assertNotNull(retrievedOrder);
    assertEquals(key, retrievedOrder.getId());
    assertEquals(items.get("yummy1"), retrievedOrder.getOrderItems().get("yummy1"));
  }

}