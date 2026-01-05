package com.ship.service;

import com.ship.dto.ShipComponentDto;
import com.ship.mapper.ShipComponentMapper;
import com.ship.model.ShipComponentEntity;
import com.ship.model.ShipEntity;
import com.ship.repository.ShipComponentRepository;
import com.ship.repository.ShipRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;


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
        return shipRepo.findByOwnerUserIdAndIdUnlocked(ownerUserId, shipId)
                .map(ship -> mapper.toDtoList(componentRepo.findByShip_Id(ship.getId())));
    }

    @Transactional
    public Optional<ShipComponentDto> addComponentToShip(Long shipId, ShipComponentDto dto, Long ownerUserId) {
        Optional<ShipEntity> optionalShipEntity = shipRepo.findByOwnerUserIdAndIdUnlocked(ownerUserId, shipId);
        if (optionalShipEntity.isEmpty())
                return Optional.empty();
        ShipEntity shipEntity = optionalShipEntity.get();

        ShipComponentEntity component = mapper.toEntity(dto);
        component.setShip(shipEntity);
        ShipComponentEntity componentSaved = componentRepo.save(component);

        return Optional.of(mapper.toDto(componentSaved));
    }

    @Transactional
    public Optional<ShipComponentDto> updateComponent(Long shipId, Long componentId, Long ownerUserId, ShipComponentDto component) {
        Optional<ShipEntity> optionalShipEntity = shipRepo.findByOwnerUserIdAndIdUnlocked(ownerUserId, shipId);
        if (optionalShipEntity.isEmpty())
            return Optional.empty();
        ShipEntity shipEntity = optionalShipEntity.get();

        return componentRepo.findById(componentId)
                .map(entity -> {
                    component.setId(shipId);

                    ShipComponentEntity updatedEntity = mapper.toEntity(component);
                    updatedEntity.setShip(shipEntity);

                    return mapper.toDto(componentRepo.save(mapper.toEntity(component)));
                });
    }

    @Transactional
    public Optional<ShipComponentDto[]> updateComponents(Long shipId, Long ownerUserId, List<ShipComponentDto> components) {
        Optional<ShipEntity> shipEntityOptional = shipRepo.findByOwnerUserIdAndId(ownerUserId, shipId);
        if (shipEntityOptional.isEmpty())
            return Optional.empty();
        ShipEntity shipEntity = shipEntityOptional.get();

        Map<Long, ShipComponentDto> incomingByTypeId = new HashMap<>();
        for (ShipComponentDto dto : components) {
            if (dto.getComponentTypeId() == null) {
                throw new IllegalArgumentException("componentTypeId is required");
            }
            incomingByTypeId.put(dto.getComponentTypeId(), dto);
        }
        Set<Long> incomingTypeIds = incomingByTypeId.keySet();

        List<ShipComponentEntity> existing = componentRepo.findByShip_Id(shipId);
        Map<Long, ShipComponentEntity> existingByTypeId = existing.stream()
                .collect(java.util.stream.Collectors.toMap(
                        ShipComponentEntity::getComponentTypeId,
                        e -> e,
                        (a, b) -> a
                ));

        // Delete non preset components
        List<ShipComponentEntity> toDelete = existing.stream()
                .filter(e -> !incomingTypeIds.contains(e.getComponentTypeId()))
                .toList();
        if (!toDelete.isEmpty()) {
            componentRepo.deleteAll(toDelete);
        }


        List<ShipComponentEntity> toUpsert = new ArrayList<>();

        for (Map.Entry<Long, ShipComponentDto> entry : incomingByTypeId.entrySet()) {
            Long typeId = entry.getKey();
            ShipComponentDto dto = entry.getValue();

            ShipComponentEntity ent = existingByTypeId.get(typeId);
            if (ent == null) {
                ent = new ShipComponentEntity();
                ent.setShip(shipEntity);
                ent.setComponentTypeId(typeId);
            }

            // Copy fields from DTO -> Entity
            // id is generated; ignore dto.id
            ent.setQuantity(dto.getQuantity());
            ent.setName(dto.getName());
            ent.setType(dto.getType());
            ent.setHealth(dto.getHealth());
            ent.setDamageThreshold(dto.getDamageThreshold());
            ent.setArmorClass(dto.getArmorClass());
            ent.setDescription(dto.getDescription());

            toUpsert.add(ent);
        }

        if (!toUpsert.isEmpty()) {
            componentRepo.saveAll(toUpsert);
        }

        List<ShipComponentEntity> finalState = componentRepo.findByShip_Id(shipId);
        ShipComponentDto[] out = finalState.stream()
                .map(mapper::toDto)
                .toArray(ShipComponentDto[]::new);

        return Optional.of(out);
    }

    public Optional<ShipComponentDto> getComponentForShip(Long shipId, Long componentId, Long ownerUserId) {
        if (shipRepo.findByOwnerUserIdAndIdUnlocked(ownerUserId, shipId).isEmpty())
            return Optional.empty();

        return componentRepo.findById(componentId)
                .filter(entity -> entity.getShip().getId().equals(shipId))
                .map(mapper::toDto);
    }

    @Transactional
    public boolean deleteComponentForShip(Long shipId, Long componentId, Long ownerUserId) {
        if (shipRepo.findByOwnerUserIdAndId(ownerUserId, shipId).isEmpty())
            return false;

        Optional<ShipComponentEntity> component = componentRepo.findById(componentId)
                .filter(entity -> entity.getShip().getId().equals(shipId));
        if (component.isEmpty()) {
            return false;
        }

        componentRepo.delete(component.get());
        return true;
    }
}
