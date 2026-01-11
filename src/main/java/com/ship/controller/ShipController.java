package com.ship.controller;

import com.ship.auth.AuthContext;
import com.ship.dto.ShipDto;
import com.ship.service.ShipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Tag(
        name = "Ships",
        description = "Operations for managing ships owned by the authenticated user"
)
@RestController
@RequestMapping("/api/ships")
public class ShipController {

    private final ShipService shipService;

    public ShipController(ShipService shipService) {
        this.shipService = shipService;
    }

    @Operation(
            summary = "List ships for current user",
            description = "Returns all ships owned by the authenticated user."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of ships owned by the authenticated user",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ShipDto.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Missing or invalid JWT"
            )
    })
    @GetMapping
    public List<ShipDto> getAllForUser() {
        Long ownerUserId = AuthContext.getOwnerUserId();
        if (ownerUserId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return shipService.getAllForUser(ownerUserId);
    }

    @Operation(
            summary = "Create a ship",
            description = "Creates a new ship for the authenticated user. "
                    + "The ownerUserId is resolved from the JWT and cannot be set by the client."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Ship successfully created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ShipDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Missing or invalid JWT"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ship could not be created"
            )
    })
    @PostMapping
    public ResponseEntity<ShipDto> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Ship data to create. Fields 'id' and 'ownerUserId' are ignored if provided.",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ShipDto.class))
            )
            @RequestBody ShipDto ship
    ) {
        Long ownerUserId = AuthContext.getOwnerUserId();
        if (ownerUserId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return shipService.create(ship, ownerUserId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Update a ship",
            description = "Updates an existing ship owned by the authenticated user. "
                    + "The ownerUserId is resolved from the JWT and cannot be set by the client."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Ship successfully updated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ShipDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Missing or invalid JWT"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ship not found for this user"
            )
    })
    @PutMapping("/{shipId}")
    public ResponseEntity<ShipDto> update(
            @PathVariable
            @Schema(description = "Ship identifier", example = "7")
            Long shipId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated ship data. Fields 'id' and 'ownerUserId' are ignored if provided.",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ShipDto.class))
            )
            @RequestBody ShipDto ship
    ) {
        Long ownerUserId = AuthContext.getOwnerUserId();
        if (ownerUserId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return shipService.updateShip(shipId, ownerUserId, ship)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Get a ship by ID",
            description = "Returns a single ship by ID if it belongs to the authenticated user."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Ship found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ShipDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Missing or invalid JWT"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ship not found for this user"
            )
    })
    @GetMapping("/{shipId}")
    public ResponseEntity<ShipDto> getById(
            @PathVariable
            @Schema(description = "Ship identifier", example = "7")
            Long shipId
    ) {
        Long ownerUserId = AuthContext.getOwnerUserId();
        if (ownerUserId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return shipService.getById(shipId, ownerUserId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Delete a ship",
            description = "Deletes a ship by ID if it belongs to the authenticated user."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Ship successfully deleted"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Missing or invalid JWT"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ship not found for this user"
            )
    })
    @DeleteMapping("/{shipId}")
    public ResponseEntity<Void> delete(
            @PathVariable
            @Schema(description = "Ship identifier", example = "7")
            Long shipId
    ) {
        Long ownerUserId = AuthContext.getOwnerUserId();
        if (ownerUserId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        if (shipService.delete(shipId, ownerUserId))
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.notFound().build();
    }
}