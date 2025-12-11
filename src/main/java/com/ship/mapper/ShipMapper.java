package com.ship.mapper;

import com.ship.dto.ShipDto;
import com.ship.model.ShipEntity;
import org.mapstruct.Mapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ShipMapper {

    @Mapping(target = "damageImmunities", expression = "java(split(entity.getDamageImmunities()))")
    @Mapping(target = "conditionImmunities", expression = "java(split(entity.getConditionImmunities()))")
    ShipDto toDto(ShipEntity entity);

    @Mapping(target = "damageImmunities", expression = "java(join(dto.getDamageImmunities()))")
    @Mapping(target = "conditionImmunities", expression = "java(join(dto.getConditionImmunities()))")
    ShipEntity toEntity(ShipDto dto);

    List<ShipDto> toDtoList(List<ShipEntity> entities);


    default List<String> split(String value) {
        if (value == null || value.isBlank()) return List.of();
        return List.of(value.split(","));
    }

    default String join(List<String> list) {
        if (list == null || list.isEmpty()) return "";
        return String.join(",", list);
    }
}
    // ---------- Helper conversions ----------
    // Splits the conditions into a list
/*
    private static List<String> split(String text) {
        if (text == null || text.isBlank()) return Collections.emptyList();
        return Arrays.stream(text.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
    }

    // Joins the list of conditions into a single string
    private static String join(List<String> list) {
        if (list == null || list.isEmpty()) return "";
        return String.join(",", list);
    }

    // Entity -> DTO
    public static ShipDto toDto(ShipEntity e) {
        if (e == null) return null;

        ShipDto dto = new ShipDto();
        dto.setId(e.getId());
        dto.setName(e.getName());
        dto.setSize(e.getSize());
        dto.setLength(e.getLength());
        dto.setWidth(e.getWidth());

        dto.setStrength(e.getStrength());
        dto.setDexterity(e.getDexterity());
        dto.setConstitution(e.getConstitution());
        dto.setIntelligence(e.getIntelligence());
        dto.setWisdom(e.getWisdom());
        dto.setCharisma(e.getCharisma());

        dto.setCrewCapacity(e.getCrewCapacity());
        dto.setPassengerCapacity(e.getPassengerCapacity());
        dto.setCargoCapacity(e.getCargoCapacity());

        dto.setDamageImmunities(split(e.getDamageImmunities()));
        dto.setConditionImmunities(split(e.getConditionImmunities()));

        dto.setActionNumber(e.getActionNumber());
        dto.setAttunementSlots(e.getAttunementSlots());

        return dto;
    }

    // DTO -> Entity
    public static ShipEntity toEntity(ShipDto dto) {
        if (dto == null) return null;

        ShipEntity e = new ShipEntity();
        e.setId(dto.getId());
        e.setName(dto.getName());
        e.setSize(dto.getSize());
        e.setLength(dto.getLength());
        e.setWidth(dto.getWidth());

        e.setStrength(dto.getStrength());
        e.setDexterity(dto.getDexterity());
        e.setConstitution(dto.getConstitution());
        e.setIntelligence(dto.getIntelligence());
        e.setWisdom(dto.getWisdom());
        e.setCharisma(dto.getCharisma());

        e.setCrewCapacity(dto.getCrewCapacity());
        e.setPassengerCapacity(dto.getPassengerCapacity());
        e.setCargoCapacity(dto.getCargoCapacity());

        e.setDamageImmunities(join(dto.getDamageImmunities()));
        e.setConditionImmunities(join(dto.getConditionImmunities()));

        e.setActionNumber(dto.getActionNumber());
        e.setAttunementSlots(dto.getAttunementSlots());

        return e;
    }
}
*/