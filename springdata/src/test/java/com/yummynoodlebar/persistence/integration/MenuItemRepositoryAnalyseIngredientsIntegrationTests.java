package com.yummynoodlebar.persistence.integration;


import com.yummynoodlebar.config.MongoConfiguration;
import com.yummynoodlebar.persistence.repository.MenuItemRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

import static com.yummynoodlebar.persistence.domain.fixture.PersistenceFixture.eggFriedRice;
import static com.yummynoodlebar.persistence.domain.fixture.PersistenceFixture.standardItem;
import static junit.framework.TestCase.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MongoConfiguration.class})
public class MenuItemRepositoryAnalyseIngredientsIntegrationTests {

  @Autowired
  MenuItemRepository menuItemRepository;

  @Autowired
  MongoOperations mongo;

  @Before
  public void setup() throws Exception {
    mongo.dropCollection("menu");
  }

  @After
  public void teardown() {
    mongo.dropCollection("menu");
  }

  @Test
  public void thatIngredientsAnalysisWorks() throws Exception {

    menuItemRepository.save(standardItem());
    menuItemRepository.save(standardItem());
    menuItemRepository.save(standardItem());
    menuItemRepository.save(eggFriedRice());
    menuItemRepository.save(eggFriedRice());

    Map<String, Integer> ingredients = menuItemRepository.analyseIngredientsByPopularity();

    assertEquals(4, ingredients.size());
    assertEquals(5, (int)ingredients.get("Egg"));
    assertEquals(3, (int)ingredients.get("Noodles"));
    assertEquals(3, (int)ingredients.get("Peanuts"));
    assertEquals(2, (int)ingredients.get("Rice"));
  }
}
