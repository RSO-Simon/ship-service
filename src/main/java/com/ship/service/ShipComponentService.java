package com.ship.service;

import com.ship.dto.ShipComponentDto;
import com.ship.mapper.ShipComponentMapper;
import com.ship.model.ShipComponentEntity;
import com.ship.model.ShipEntity;
import com.ship.repository.ShipComponentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ShipComponentService {

    private final ShipComponentRepository repo;
    private final ShipComponentMapper mapper;

    public ShipComponentService(ShipComponentRepository repo, ShipComponentMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public List<ShipComponentDto> getComponentsForShip(long shipId) {
        List<ShipComponentEntity> components = repo.findByShipId(shipId);
        return mapper.toDtoList(components);
    }

    public ShipComponentDto addComponentToShip(long shipId, ShipComponentDto dto) {
        dto.setShipId(shipId);
        ShipComponentEntity component = mapper.toEntity(dto);
        ShipComponentEntity componentSaved = repo.save(component);

        return mapper.toDto(componentSaved);
    }

    public Optional<ShipComponentDto> getComponentForShip(long shipId, long componentId) {
        return repo.findById(componentId)
                .filter(entity -> entity.getShipId().equals(shipId))
                .map(mapper::toDto);
    }

    public boolean deleteComponentForShip(long shipId, long componentId) {
        Optional<ShipComponentEntity> component = repo.findById(componentId)
                .filter(entity -> entity.getShipId().equals(shipId));
        if (component.isEmpty()) {
            return false;
        }

        repo.delete(component.get());
        return true;
    }
}
