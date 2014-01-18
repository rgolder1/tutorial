package com.yummynoodlebar.persistence.services;

import com.gemstone.gemfire.cache.query.CqEvent;
import com.yummynoodlebar.core.services.OrderStatusUpdateService;
import com.yummynoodlebar.events.orders.SetOrderStatusEvent;
import com.yummynoodlebar.persistence.domain.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;

public class StatusUpdateGemfireNotificationListener {

  @Autowired
  private OrderStatusUpdateService orderStatusUpdateService;

  public void setOrderStatusUpdateService(OrderStatusUpdateService orderStatusUpdateService) {
    this.orderStatusUpdateService = orderStatusUpdateService;
  }

  public void handleEvent(CqEvent event) {

    if (!event.getBaseOperation().isCreate()) {
      return;
    }

    OrderStatus status = (OrderStatus) event.getNewValue();

    orderStatusUpdateService.setOrderStatus(
        new SetOrderStatusEvent(status.getOrderId(),
                                status.toStatusDetails()));

  }
}