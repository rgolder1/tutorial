package com.yummynoodlebar.persistence.integration;


import com.yummynoodlebar.config.JPAConfiguration;
import com.yummynoodlebar.persistence.domain.Order;
import com.yummynoodlebar.persistence.domain.fixture.PersistenceFixture;
import com.yummynoodlebar.persistence.repository.OrdersRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JPAConfiguration.class})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class OrdersRepositoryFindOrdersContainingTests {

  @Autowired
  OrdersRepository ordersRepository;

  @Autowired
  EntityManager entityManager;

  @Test
  public void thatSearchingForOrdesContainingWorks() throws Exception {

    ordersRepository.save(PersistenceFixture.standardOrder());
    ordersRepository.save(PersistenceFixture.standardOrder());
    ordersRepository.save(PersistenceFixture.yummy16Order());
    ordersRepository.save(PersistenceFixture.yummy16Order());

    List<Order> retrievedOrders = ordersRepository.findOrdersContaining("yummy16");

    assertNotNull(retrievedOrders);
    assertEquals(2, retrievedOrders.size());
    assertEquals(22, (int) retrievedOrders.get(0).getOrderItems().get("yummy16"));

    retrievedOrders = ordersRepository.findOrdersContaining("yummy3");

    assertNotNull(retrievedOrders);
    assertEquals(2, retrievedOrders.size());
  }

}