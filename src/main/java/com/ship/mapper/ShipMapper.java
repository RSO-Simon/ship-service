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