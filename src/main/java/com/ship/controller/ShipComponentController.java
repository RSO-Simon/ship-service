package com.ship.controller;


import com.ship.dto.ShipComponentDto;
import com.ship.service.ShipComponentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ships/{shipId}/components")
public class ShipComponentController {

    private final ShipComponentService service;

    public ShipComponentController(ShipComponentService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ShipComponentDto>> getShipComponents(
            @PathVariable("shipId") Long shipId,
            @RequestParam Long ownerUserId

    ) {
        return service.getComponentsForShip(shipId, ownerUserId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ShipComponentDto> addComponent(
            @PathVariable("shipId") Long shipId,
            @RequestBody ShipComponentDto dto,
            @RequestParam Long ownerUserId
    ) {
        return service.addComponentToShip(shipId, dto, ownerUserId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{componentId}")
    public ResponseEntity<ShipComponentDto> update(
            @PathVariable Long shipId,
            @PathVariable Long componentId,
            @RequestParam Long ownerUserId,
            @RequestBody ShipComponentDto component
    ) {
        return service.updateComponent(shipId, componentId, ownerUserId, component)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<ShipComponentDto[]> updateComponents(
            @PathVariable Long shipId,
            @RequestParam Long ownerUserId,
            @RequestBody List<ShipComponentDto> components
    ) {
        return service.updateComponents(shipId, ownerUserId, components)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{componentId}")
    public ResponseEntity<ShipComponentDto> getComponent(
            @PathVariable Long shipId,
            @PathVariable Long componentId,
            @RequestParam Long ownerUserId
    ) {
        return service.getComponentForShip(shipId, componentId, ownerUserId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{componentId}")
    public ResponseEntity<Void> deleteComponent(
            @PathVariable Long shipId,
            @PathVariable Long componentId,
            @RequestParam Long ownerUserId
    ) {
        if(service.deleteComponentForShip(shipId, componentId, ownerUserId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
