package com.yummynoodlebar.config;

import com.yummynoodlebar.persistence.repository.OrderStatusRepository;
import com.yummynoodlebar.persistence.services.StatusUpdateGemfireNotificationListener;
import org.springframework.context.annotation.*;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ImportResource({"classpath:gemfire/client.xml"})
@EnableTransactionManagement
@EnableGemfireRepositories(basePackages = "com.yummynoodlebar.persistence.repository",
    includeFilters = @ComponentScan.Filter(value = {OrderStatusRepository.class}, type = FilterType.ASSIGNABLE_TYPE))
public class GemfireConfiguration {

	  @Bean public StatusUpdateGemfireNotificationListener statusUpdateListener() {
		    return new StatusUpdateGemfireNotificationListener();
	  }

}
