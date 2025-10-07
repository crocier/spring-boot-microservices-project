package com.myprojects.inventory_service.controller;

import com.myprojects.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{product}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@PathVariable String product){
        return inventoryService.isInStock(product);
    }
}
