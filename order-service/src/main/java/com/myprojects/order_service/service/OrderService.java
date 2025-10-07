package com.myprojects.order_service.service;

import com.myprojects.order_service.dto.OrderLineItemsDto;
import com.myprojects.order_service.dto.OrderRequest;
import com.myprojects.order_service.model.Order;
import com.myprojects.order_service.model.OrderLineItems;
import com.myprojects.order_service.repository.OrderRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Builder
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest) {
        List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLineItemsListDto()
                .stream().map(this::mapToOrderEntity).toList();
        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .orderLineItemsList(orderLineItemsList)
                .build();
        orderRepository.save(order);
    }

    private OrderLineItems mapToOrderEntity(OrderLineItemsDto orderLineItemDto) {
        return OrderLineItems.builder()
                .product(orderLineItemDto.getProduct())
                .price(orderLineItemDto.getPrice())
                .quantity(orderLineItemDto.getQuantity())
                .build();
    }



}
