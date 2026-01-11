package com.ship.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class ShipDto {

    @Schema(
            description = "Unique identifier of the ship",
            example = "7",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id;

    // Ownership
    @Schema(
            description = "Identifier of the user who owns this ship",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long ownerUserId;

    // Header info
    @Schema(
            description = "Name of the ship",
            example = "The Flying Dutchman"
    )
    private String name;

    @Schema(
            description = "Size category of the ship",
            example = "Huge"
    )
    private String size;

    @Schema(
            description = "Length of the ship in feet",
            example = "120"
    )
    private int length;

    @Schema(
            description = "Width of the ship in feet",
            example = "30"
    )
    private int width;

    // Stats
    @Schema(
            description = "Strength ability score of the ship",
            example = "18"
    )
    private int strength;

    @Schema(
            description = "Dexterity ability score of the ship",
            example = "10"
    )
    private int dexterity;

    @Schema(
            description = "Constitution ability score of the ship",
            example = "16"
    )
    private int constitution;

    @Schema(
            description = "Intelligence ability score of the ship",
            example = "2"
    )
    private int intelligence;

    @Schema(
            description = "Wisdom ability score of the ship",
            example = "8"
    )
    private int wisdom;

    @Schema(
            description = "Charisma ability score of the ship",
            example = "12"
    )
    private int charisma;

    // General
    @Schema(
            description = "Maximum number of crew members the ship can carry",
            example = "20"
    )
    private int crewCapacity;

    @Schema(
            description = "Maximum number of passengers the ship can carry",
            example = "10"
    )
    private int passengerCapacity;

    @Schema(
            description = "Cargo capacity of the ship in tons",
            example = "50"
    )
    private int cargoCapacity;

    // Defenses
    @Schema(
            description = "List of damage types the ship is immune to",
            example = "[\"poison\", \"psychic\"]"
    )
    private List<String> damageImmunities;

    @Schema(
            description = "List of conditions the ship is immune to",
            example = "[\"charmed\", \"frightened\"]"
    )
    private List<String> conditionImmunities;

    // Actions
    private int actionNumber;

    // Slots
    private int attunementSlots;

    public ShipDto() {}

    public Long getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(Long ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getConstitution() {
        return constitution;
    }

    public void setConstitution(int constitution) {
        this.constitution = constitution;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getWisdom() {
        return wisdom;
    }

    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
    }

    public int getCharisma() {
        return charisma;
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }

    public int getCrewCapacity() {
        return crewCapacity;
    }

    public void setCrewCapacity(int crewCapacity) {
        this.crewCapacity = crewCapacity;
    }

    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    public void setPassengerCapacity(int passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
    }

    public int getCargoCapacity() {
        return cargoCapacity;
    }

    public void setCargoCapacity(int cargoCapacity) {
        this.cargoCapacity = cargoCapacity;
    }

    public List<String> getDamageImmunities() {
        return damageImmunities;
    }

    public void setDamageImmunities(List<String> damageImmunities) {
        this.damageImmunities = damageImmunities;
    }

    public List<String> getConditionImmunities() {
        return conditionImmunities;
    }

    public void setConditionImmunities(List<String> conditionImmunities) {
        this.conditionImmunities = conditionImmunities;
    }

    public int getActionNumber() {
        return actionNumber;
    }

    public void setActionNumber(int actionNumber) {
        this.actionNumber = actionNumber;
    }

    public int getAttunementSlots() {
        return attunementSlots;
    }

    public void setAttunementSlots(int attunementSlots) {
        this.attunementSlots = attunementSlots;
    }
}
