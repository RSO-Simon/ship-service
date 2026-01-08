package com.ship.repository;

import com.ship.model.ShipComponentEntity;
import com.ship.model.ShipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipComponentRepository extends JpaRepository<ShipComponentEntity, Long> {
    List<ShipComponentEntity> findByShip_Id(Long shipId);
    void deleteByShip(ShipEntity shipId);

}
