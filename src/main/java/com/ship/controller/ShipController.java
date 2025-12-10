package com.ship.controller;

import com.ship.dto.ShipDto;
import com.ship.service.ShipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ships")
public class ShipController {

    private final ShipService shipService;

    public ShipController(ShipService shipService) {
        this.shipService = shipService;
    }

    @GetMapping
    public List<ShipDto> getAll() {
        return shipService.getAll();
    }

    @PostMapping
    public ShipDto create(@RequestBody ShipDto ship) {
        return shipService.create(ship);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShipDto> getById(@PathVariable Long id) {
        return shipService.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (shipService.delete(id))
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("/hello")
    public String hello() {
        return "Ship service is alive!";
    }
}