package com.ship.controller;


import com.ship.auth.AuthContext;
import com.ship.dto.ShipComponentDto;
import com.ship.service.ShipComponentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/{shipId}/components")
public class ShipComponentController {

    private final ShipComponentService service;

    public ShipComponentController(ShipComponentService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ShipComponentDto>> getShipComponents(
            @PathVariable("shipId") Long shipId
    ) {
        Long ownerUserId = AuthContext.getOwnerUserId();
        if (ownerUserId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return service.getComponentsForShip(shipId, ownerUserId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ShipComponentDto> addComponent(
            @PathVariable("shipId") Long shipId,
            @RequestBody ShipComponentDto dto
    ) {
        Long ownerUserId = AuthContext.getOwnerUserId();
        if (ownerUserId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return service.addComponentToShip(shipId, dto, ownerUserId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{componentId}")
    public ResponseEntity<ShipComponentDto> update(
            @PathVariable Long shipId,
            @PathVariable Long componentId,
            @RequestBody ShipComponentDto component
    ) {
        Long ownerUserId = AuthContext.getOwnerUserId();
        if (ownerUserId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return service.updateComponent(shipId, componentId, ownerUserId, component)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<ShipComponentDto[]> updateComponents(
            @PathVariable Long shipId,
            @RequestBody List<ShipComponentDto> components
    ) {
        Long ownerUserId = AuthContext.getOwnerUserId();
        if (ownerUserId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return service.updateComponents(shipId, ownerUserId, components)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{componentId}")
    public ResponseEntity<ShipComponentDto> getComponent(
            @PathVariable Long shipId,
            @PathVariable Long componentId
    ) {
        Long ownerUserId = AuthContext.getOwnerUserId();
        if (ownerUserId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return service.getComponentForShip(shipId, componentId, ownerUserId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{componentId}")
    public ResponseEntity<Void> deleteComponent(
            @PathVariable Long shipId,
            @PathVariable Long componentId
    ) {
        Long ownerUserId = AuthContext.getOwnerUserId();
        if (ownerUserId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        if(service.deleteComponentForShip(shipId, componentId, ownerUserId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
