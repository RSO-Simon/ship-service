package com.ship.service;

import com.ship.dto.ShipDto;
import com.ship.mapper.ShipMapper;
import com.ship.model.ShipEntity;
import com.ship.repository.ShipRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ShipService {

    private final ShipRepository repo;
    private final ShipMapper mapper;

    public ShipService(ShipRepository repo, ShipMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public List<ShipDto> getAllForUser(Long userId) {
        return mapper.toDtoList(repo.findByOwnerUserId(userId));
    }

    @Transactional
    public Optional<ShipDto> create(ShipDto ship, Long ownerUserId) {
        if (!ship.getOwnerUserId().equals(ownerUserId)) {
            return Optional.empty();
        }
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
        if (entity.isEmpty() || !entity.get().getOwnerUserId().equals(ownerUserId)) {
            return false;
        }

        repo.delete(entity.get());
        return true;
    }
}
