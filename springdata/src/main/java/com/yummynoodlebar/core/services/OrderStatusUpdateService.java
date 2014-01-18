package com.yummynoodlebar.core.services;


import com.yummynoodlebar.events.orders.OrderStatusEvent;
import com.yummynoodlebar.events.orders.SetOrderStatusEvent;

public interface OrderStatusUpdateService {

  OrderStatusEvent setOrderStatus(SetOrderStatusEvent orderStatusEvent);

}
