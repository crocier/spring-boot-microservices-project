package com.myprojects.inventory_service.service;

import com.myprojects.inventory_service.dto.InventoryResponse;
import com.myprojects.inventory_service.model.Inventory;
import com.myprojects.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> productList){
        return inventoryRepository.findByProductIn(productList).stream()
                .map(inventory -> InventoryResponse.builder()
                        .product(inventory.getProduct())
                        .isInStock(inventory.getQuantity() > 0)
                        .build()
                ).toList();
    }

}
