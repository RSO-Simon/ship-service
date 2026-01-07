package com.ship.controller;

import com.ship.dto.ShipDto;
import com.ship.service.ShipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class ShipController {

    private final ShipService shipService;

    public ShipController(ShipService shipService) {
        this.shipService = shipService;
    }

    @GetMapping
    public List<ShipDto> getAllForUser(
            @RequestParam Long ownerUserId
    ) {
        return shipService.getAllForUser(ownerUserId);
    }

    @PostMapping
    public ResponseEntity<ShipDto> create(
            @RequestBody ShipDto ship,
            @RequestParam Long ownerUserId
    ) {
        return shipService.create(ship, ownerUserId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{shipId}")
    public ResponseEntity<ShipDto> update(
            @PathVariable Long shipId,
            @RequestParam Long ownerUserId,
            @RequestBody ShipDto ship
    ) {
        return shipService.updateShip(shipId, ownerUserId, ship)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{shipId}")
    public ResponseEntity<ShipDto> getById(
            @PathVariable Long shipId,
            @RequestParam Long ownerUserId
    ) {
        return shipService.getById(shipId, ownerUserId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{shipId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long shipId,
            @RequestParam Long ownerUserId
    ) {
        if (shipService.delete(shipId, ownerUserId))
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("/hello")
    public String hello() {
        return "Ship service is alive!";
    }
}