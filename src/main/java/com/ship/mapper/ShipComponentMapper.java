package com.ship.mapper;

import com.ship.dto.ShipComponentDto;
import com.ship.model.ShipComponentEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ShipComponentMapper {

    ShipComponentDto toDto(ShipComponentEntity entity);

    ShipComponentEntity toEntity(ShipComponentDto dto);

    List<ShipComponentDto> toDtoList(List<ShipComponentEntity> entities);
}
