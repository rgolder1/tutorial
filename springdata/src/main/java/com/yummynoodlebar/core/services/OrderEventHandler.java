package com.yummynoodlebar.core.services;

import com.yummynoodlebar.core.domain.Order;
import com.yummynoodlebar.events.orders.*;
import com.yummynoodlebar.persistence.services.OrderPersistenceService;

import java.util.Date;
import java.util.UUID;

public class OrderEventHandler implements OrderService {

  private final OrderPersistenceService ordersPersistenceService;

  public OrderEventHandler(final OrderPersistenceService ordersPersistenceService) {
    this.ordersPersistenceService = ordersPersistenceService;
  }

  @Override
  public OrderCreatedEvent createOrder(CreateOrderEvent createOrderEvent) {

    Order order = Order.fromOrderDetails(createOrderEvent.getDetails());

    //TODO, add validation of menu items
    //TODO, add order total calculation
    //TODO, add order time estimate calculation

    OrderCreatedEvent event = ordersPersistenceService.createOrder(createOrderEvent);

    //TODO, where should this go?
    OrderStatusEvent orderStatusEvent = ordersPersistenceService.setOrderStatus(
        new SetOrderStatusEvent(order.getKey(), new OrderStatusDetails(order.getKey(),
        UUID.randomUUID(), new Date(), "Order Created")));

    return event;
  }

  @Override
  public AllOrdersEvent requestAllOrders(RequestAllOrdersEvent requestAllCurrentOrdersEvent) {
    return ordersPersistenceService.requestAllOrders(requestAllCurrentOrdersEvent);
  }

  @Override
  public OrderDetailsEvent requestOrderDetails(RequestOrderDetailsEvent requestOrderDetailsEvent) {
    return ordersPersistenceService.requestOrderDetails(requestOrderDetailsEvent);
  }

  @Override
  public OrderUpdatedEvent setOrderPayment(SetOrderPaymentEvent setOrderPaymentEvent) {
    return ordersPersistenceService.setOrderPayment(setOrderPaymentEvent);
  }

  @Override
  public OrderDeletedEvent deleteOrder(DeleteOrderEvent deleteOrderEvent) {

    OrderDetailsEvent orderDetailsEvent = ordersPersistenceService.requestOrderDetails(new RequestOrderDetailsEvent(deleteOrderEvent.getKey()));

    if (!orderDetailsEvent.isEntityFound()) {
      return OrderDeletedEvent.notFound(deleteOrderEvent.getKey());
    }

    Order order = Order.fromOrderDetails(orderDetailsEvent.getOrderDetails());

    if (!order.canBeDeleted()) {
      return OrderDeletedEvent.deletionForbidden(deleteOrderEvent.getKey(), order.toOrderDetails());
    }

    ordersPersistenceService.deleteOrder(deleteOrderEvent);

    return new OrderDeletedEvent(deleteOrderEvent.getKey(), order.toOrderDetails());
  }

  @Override
  public OrderStatusEvent requestOrderStatus(RequestOrderStatusEvent requestOrderDetailsEvent) {
    return ordersPersistenceService.requestOrderStatus(requestOrderDetailsEvent);
  }
}
