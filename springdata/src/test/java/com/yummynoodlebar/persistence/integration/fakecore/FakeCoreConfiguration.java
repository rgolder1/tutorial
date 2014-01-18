package com.yummynoodlebar.persistence.integration.fakecore;

import com.yummynoodlebar.core.services.OrderStatusUpdateService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FakeCoreConfiguration {

  @Bean OrderStatusUpdateService orderStatusUpdateService() {
    return new CountingOrderStatusService();
  }
}