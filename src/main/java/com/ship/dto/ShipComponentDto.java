package com.ship.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class ShipComponentDto {

    @Schema(
            description = "Unique identifier of the ship component",
            example = "42",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id;

    @Schema(
            description = "Identifier of the component type definition",
            example = "5"
    )
    private Long componentTypeId;

    @Schema(
            description = "Number of components of this type installed on the ship",
            example = "2"
    )
    private int quantity;

    // Component data
    @Schema(
            description = "Display name of the component",
            example = "Ballista"
    )
    private String name;

    @Schema(
            description = "Component category or type",
            example = "Weapon"
    )
    private String type;

    @Schema(
            description = "Current or maximum health of the component",
            example = "50"
    )
    private int health;

    @Schema(
            description = "Minimum damage required to affect this component",
            example = "10"
    )
    private int damageThreshold;

    @Schema(
            description = "Armor Class (AC) of the component",
            example = "15"
    )
    private int armorClass;

    @Schema(
            description = "Detailed description of the component",
            example = "Ranged Weapon Attack: +5 to hit, range 200/800 ft. Hit: 27 (5d10) bludgeoning damage. Can't hit targets within 60 ft. of it. "
    )
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getComponentTypeId() {
        return componentTypeId;
    }

    public void setComponentTypeId(Long componentTypeId) {
        this.componentTypeId = componentTypeId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDamageThreshold() {
        return damageThreshold;
    }

    public void setDamageThreshold(int damageThreshold) {
        this.damageThreshold = damageThreshold;
    }

    public int getArmorClass() {
        return armorClass;
    }

    public void setArmorClass(int armorClass) {
        this.armorClass = armorClass;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
