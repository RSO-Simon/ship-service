package com.ship;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ships")
public class ShipController {

    @GetMapping("/hello")
    public String hello() {
        return "Ship service is alive!";
    }
}