package com.ship.service;

import com.ship.dto.ShipComponentDto;
import com.ship.mapper.ShipComponentMapper;
import com.ship.model.ShipComponentEntity;
import com.ship.repository.ShipComponentRepository;
import com.ship.repository.ShipRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ShipComponentService {

    private final ShipComponentRepository componentRepo;
    private final ShipRepository shipRepo;
    private final ShipComponentMapper mapper;

    public ShipComponentService(ShipComponentRepository componentRepo, ShipRepository shipRepo, ShipComponentMapper mapper) {
        this.componentRepo = componentRepo;
        this.shipRepo = shipRepo;
        this.mapper = mapper;
    }

    public Optional<List<ShipComponentDto>> getComponentsForShip(Long shipId, Long ownerUserId) {
        return shipRepo.findByOwnerUserIdAndId(ownerUserId, shipId)
                .map(ship -> mapper.toDtoList(componentRepo.findByShipId(ship.getId())));
    }

    @Transactional
    public Optional<ShipComponentDto> addComponentToShip(Long shipId, ShipComponentDto dto, Long ownerUserId) {
        if (shipRepo.findByOwnerUserIdAndId(ownerUserId, shipId).isEmpty())
                return Optional.empty();

        dto.setShipId(shipId);
        ShipComponentEntity component = mapper.toEntity(dto);
        ShipComponentEntity componentSaved = componentRepo.save(component);

        return Optional.of(mapper.toDto(componentSaved));
    }

    @Transactional
    public Optional<ShipComponentDto> updateComponent(Long shipId, Long componentId, Long ownerUserId, ShipComponentDto component) {
        if (shipRepo.findByOwnerUserIdAndId(ownerUserId, shipId).isEmpty())
            return Optional.empty();

        return componentRepo.findById(componentId)
                .map(entity -> {
                    component.setId(shipId);
                    component.setShipId(shipId);

                    return mapper.toDto(componentRepo.save(mapper.toEntity(component)));
                });
    }

    public Optional<ShipComponentDto> getComponentForShip(Long shipId, Long componentId, Long ownerUserId) {
        if (shipRepo.findByOwnerUserIdAndId(ownerUserId, shipId).isEmpty())
            return Optional.empty();

        return componentRepo.findById(componentId)
                .filter(entity -> entity.getShipId().equals(shipId))
                .map(mapper::toDto);
    }

    @Transactional
    public boolean deleteComponentForShip(Long shipId, Long componentId, Long ownerUserId) {
        if (shipRepo.findByOwnerUserIdAndId(ownerUserId, shipId).isEmpty())
            return false;

        Optional<ShipComponentEntity> component = componentRepo.findById(componentId)
                .filter(entity -> entity.getShipId().equals(shipId));
        if (component.isEmpty()) {
            return false;
        }

        componentRepo.delete(component.get());
        return true;
    }
}
