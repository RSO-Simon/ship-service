package com.ship.service;

import com.ship.dto.ShipDto;
import com.ship.mapper.ShipMapper;
import com.ship.model.ShipEntity;
import com.ship.repository.ShipRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class ShipService {

    private final ShipRepository repo;

    public ShipService(ShipRepository repo) {
        this.repo = repo;
    }

    public List<ShipDto> getAll() {
        return repo.findAll().stream().map(ShipMapper::toDto).collect(Collectors.toList());
    }

    public ShipDto create(ShipDto ship) {
        ShipEntity shipEntity = ShipMapper.toEntity(ship);
        return ShipMapper.toDto(repo.save(shipEntity));
    }

    public Optional<ShipDto> getById(Long id) {
        return repo.findById(id).map(ShipMapper::toDto);
    }

    public boolean delete(Long id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }
}
