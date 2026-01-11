package com.ship.controller;


import com.ship.auth.AuthContext;
import com.ship.dto.ShipComponentDto;
import com.ship.service.ShipComponentService;
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
        name = "Ship Components",
        description = "Operations for managing components installed on a ship owned by the authenticated user"
)
@RestController
@RequestMapping("/{shipId}/components")
public class ShipComponentController {

    private final ShipComponentService service;

    public ShipComponentController(ShipComponentService service) {
        this.service = service;
    }

    @Operation(
            summary = "List components for a ship",
            description = "Returns all components installed on the given ship if the ship belongs to the authenticated user."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of components for the ship",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ShipComponentDto.class))
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
    @GetMapping
    public ResponseEntity<List<ShipComponentDto>> getShipComponents(
            @PathVariable("shipId")
            @Schema(description = "Ship identifier", example = "7")
            Long shipId
    ) {
        Long ownerUserId = AuthContext.getOwnerUserId();
        if (ownerUserId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return service.getComponentsForShip(shipId, ownerUserId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Add a component to a ship",
            description = "Adds a component to the given ship if it belongs to the authenticated user. "
                    + "The component 'id' is server-generated."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Component successfully added to the ship",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ShipComponentDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Missing or invalid JWT"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ship not found for this user (or component type not found)"
            )
    })
    @PostMapping
    public ResponseEntity<ShipComponentDto> addComponent(
            @PathVariable("shipId")
            @Schema(description = "Ship identifier", example = "7")
            Long shipId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Component to add. Field 'id' is ignored if provided.",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ShipComponentDto.class))
            )
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

    @Operation(
            summary = "Update a specific component on a ship",
            description = "Updates a single component instance installed on the given ship if the ship belongs to the authenticated user."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Component successfully updated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ShipComponentDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Missing or invalid JWT"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ship or component not found for this user"
            )
    })
    @PutMapping("/{componentId}")
    public ResponseEntity<ShipComponentDto> update(
            @PathVariable
            @Schema(description = "Ship identifier", example = "7")
            Long shipId,
            @PathVariable
            @Schema(description = "Ship component identifier", example = "42")
            Long componentId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated component data. Field 'id' is ignored if provided.",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ShipComponentDto.class))
            )
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

    @Operation(
            summary = "Bulk update components on a ship",
            description = "Replaces/updates the list of components installed on the given ship if it belongs to the authenticated user."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Components successfully updated",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ShipComponentDto.class))
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
    @PutMapping
    public ResponseEntity<List<ShipComponentDto>> updateComponents(
            @PathVariable
            @Schema(description = "Ship identifier", example = "7")
            Long shipId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "List of components to update for the ship. Component 'id' values identify existing instances.",
                    required = true,
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = ShipComponentDto.class))
                    )
            )
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

    @Operation(
            summary = "Get a component on a ship by ID",
            description = "Returns a single component instance installed on the given ship if the ship belongs to the authenticated user."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Component found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ShipComponentDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Missing or invalid JWT"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ship or component not found for this user"
            )
    })
    @GetMapping("/{componentId}")
    public ResponseEntity<ShipComponentDto> getComponent(
            @PathVariable
            @Schema(description = "Ship identifier", example = "7")
            Long shipId,
            @PathVariable
            @Schema(description = "Ship component identifier", example = "42")
            Long componentId
    ) {
        Long ownerUserId = AuthContext.getOwnerUserId();
        if (ownerUserId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return service.getComponentForShip(shipId, componentId, ownerUserId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Delete a component from a ship",
            description = "Deletes a component instance from the given ship if the ship belongs to the authenticated user."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Component successfully deleted"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Missing or invalid JWT"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ship or component not found for this user"
            )
    })
    @DeleteMapping("/{componentId}")
    public ResponseEntity<Void> deleteComponent(
            @PathVariable
            @Schema(description = "Ship identifier", example = "7")
            Long shipId,
            @PathVariable
            @Schema(description = "Ship component identifier", example = "42")
            Long componentId
    ) {
        Long ownerUserId = AuthContext.getOwnerUserId();
        if (ownerUserId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        if (service.deleteComponentForShip(shipId, componentId, ownerUserId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}