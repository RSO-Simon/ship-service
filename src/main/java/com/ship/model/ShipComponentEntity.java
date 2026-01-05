package com.ship.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ship_components",
uniqueConstraints = @UniqueConstraint(
        name = "uk_ship_component_type",
        columnNames = {"ship_id", "component_type_id"}
))
public class ShipComponentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Ownership ;)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ship_id", nullable = false)
    private ShipEntity ship;

    @Column(name = "component_type_id", nullable = false)
    private Long componentTypeId;

    private int quantity;

    // Component data
    private String name;
    private String type;
    private int health;
    private int damageThreshold;
    private int armorClass;
    private String description;

    public ShipComponentEntity() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ShipEntity getShip() {
        return ship;
    }

    public void setShip(ShipEntity ship) {
        this.ship = ship;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDamageThreshold() {
        return damageThreshold;
    }

    public void setDamageThreshold(int damageThreshold) {
        this.damageThreshold = damageThreshold;
    }
}
