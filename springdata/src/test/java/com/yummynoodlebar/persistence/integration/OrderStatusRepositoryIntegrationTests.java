package com.yummynoodlebar.persistence.integration;


import com.yummynoodlebar.config.GemfireConfiguration;
import com.yummynoodlebar.persistence.domain.OrderStatus;
import com.yummynoodlebar.persistence.domain.fixture.PersistenceFixture;
import com.yummynoodlebar.persistence.integration.fakecore.FakeCoreConfiguration;
import com.yummynoodlebar.persistence.repository.OrderStatusRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {FakeCoreConfiguration.class, GemfireConfiguration.class})
public class OrderStatusRepositoryIntegrationTests {

  @Autowired
  OrderStatusRepository ordersStatusRepository;

  @Before
  public void setup() {
    ordersStatusRepository.deleteAll();
  }

  @After
  public void teardown() {
    ordersStatusRepository.deleteAll();
  }

  @Test
  public void thatItemIsInsertedIntoRepoWorks() throws Exception {

    UUID key = UUID.randomUUID();

    OrderStatus orderStatus = PersistenceFixture.startedCooking(key);
    orderStatus.setId(key);

    ordersStatusRepository.save(orderStatus);

    OrderStatus retrievedOrderStatus = ordersStatusRepository.findOne(key);

    assertNotNull(retrievedOrderStatus);
    assertEquals(key, retrievedOrderStatus.getId());
  }
}