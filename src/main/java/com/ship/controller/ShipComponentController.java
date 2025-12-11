package com.ship.controller;


import com.ship.dto.ShipComponentDto;
import com.ship.service.ShipComponentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ships/{shipId}/components")
public class ShipComponentController {

    private ShipComponentService service;

    public ShipComponentController(ShipComponentService service) {
        this.service = service;
    }

    @GetMapping
    public List<ShipComponentDto> getShipComponents(@PathVariable("shipId") Long shipId) {
        return service.getComponentsForShip(shipId);
    }

    @PostMapping
    public ShipComponentDto addComponent(
            @PathVariable("shipId") Long shipId,
            @RequestBody ShipComponentDto dto
    ) {
        return service.addComponentToShip(shipId, dto);
    }

    @GetMapping("/{componentId}")
    public ResponseEntity<ShipComponentDto> getComponent(
            @PathVariable Long shipId,
            @PathVariable Long componentId
    ) {
        return service.getComponentForShip(shipId, componentId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{componentId}")
    public ResponseEntity<Void> deleteComponent(
            @PathVariable Long shipId,
            @PathVariable Long componentId
    ) {
        if(service.deleteComponentForShip(shipId, componentId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
