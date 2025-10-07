package com.myprojects.order_service.service;

import com.myprojects.order_service.dto.InventoryResponse;
import com.myprojects.order_service.dto.OrderLineItemsDto;
import com.myprojects.order_service.dto.OrderRequest;
import com.myprojects.order_service.model.Order;
import com.myprojects.order_service.model.OrderLineItems;
import com.myprojects.order_service.repository.OrderRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Builder
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;

    public void placeOrder(OrderRequest orderRequest) {
        List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLineItemsListDto()
                .stream().map(this::mapToOrderEntity).toList();
        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .orderLineItemsList(orderLineItemsList)
                .build();

        List<String> requestProductList = orderRequest.getOrderLineItemsListDto()
                .stream()
                .map(orderLineItemsDto -> orderLineItemsDto.getProduct()).toList();

        //call inventory service and place order if the product is in stock
        InventoryResponse[] inventoryResponses = webClient.get()
                .uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("product", requestProductList).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();
        boolean allProductsInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);
        if (allProductsInStock) {
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Product not in stock, try again later");
        }

    }

    private OrderLineItems mapToOrderEntity(OrderLineItemsDto orderLineItemDto) {
        return OrderLineItems.builder()
                .product(orderLineItemDto.getProduct())
                .price(orderLineItemDto.getPrice())
                .quantity(orderLineItemDto.getQuantity())
                .build();
    }


}
