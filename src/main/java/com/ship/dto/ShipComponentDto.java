package com.ship.dto;

public class ShipComponentDto {

    private Long id;

    // TMP TO DO fix
    private Long componentTypeId;

    private int quantity;

    // Component data
    private String name;
    private String type;
    private int health;
    private int damageThreshold;
    private int armorClass;
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
