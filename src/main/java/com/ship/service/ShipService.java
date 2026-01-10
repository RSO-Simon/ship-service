package com.ship.service;

import com.ship.dto.ShipDto;
import com.ship.mapper.ShipMapper;
import com.ship.model.ShipEntity;
import com.ship.repository.ShipComponentRepository;
import com.ship.repository.ShipRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ShipService {

    private final ShipRepository repo;
    private final ShipMapper mapper;
    private final ShipComponentRepository shipComponentRepository;

    public ShipService(ShipRepository repo, ShipMapper mapper, ShipComponentRepository shipComponentRepository) {
        this.repo = repo;
        this.mapper = mapper;
        this.shipComponentRepository = shipComponentRepository;
    }

    public List<ShipDto> getAllForUser(Long userId) {
        return mapper.toDtoList(repo.findByOwnerUserId(userId));
    }

    @Transactional
    public Optional<ShipDto> create(ShipDto ship, Long ownerUserId) {
        ship.setOwnerUserId(ownerUserId);
        ShipEntity entity = mapper.toEntity(ship);
        return Optional.of(mapper.toDto(repo.save(entity)));
    }

    @Transactional
    public Optional<ShipDto> updateShip(Long shipId, Long ownerUserId, ShipDto ship) {
        return repo.findByOwnerUserIdAndId(ownerUserId, shipId)
                .map(entity -> {
                    ship.setId(shipId);
                    ship.setOwnerUserId(ownerUserId);

                    return mapper.toDto(repo.save(mapper.toEntity(ship)));
                });
    }

    public Optional<ShipDto> getById(Long shipId, Long ownerUserId) {
        return repo.findById(shipId)
                .filter(entity -> entity.getOwnerUserId().equals(ownerUserId))
                .map(mapper::toDto);
    }

    @Transactional
    public boolean delete(Long shipId, Long ownerUserId) {
        Optional<ShipEntity> entity = repo.findById(shipId);
        if (entity.isEmpty()) {
            return false;
        }
        shipComponentRepository.deleteByShip(entity.get());

        repo.delete(entity.get());
        return true;
    }
}
