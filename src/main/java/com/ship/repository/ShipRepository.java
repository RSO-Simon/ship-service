package com.ship.repository;

import com.ship.model.ShipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShipRepository extends JpaRepository<ShipEntity, Long> {
    List<ShipEntity> findByOwnerUserId(Long userId);

    @Lock(jakarta.persistence.LockModeType.PESSIMISTIC_READ)
    Optional<ShipEntity> findByOwnerUserIdAndId(Long userId, Long shipId);

    @Query("select s from ShipEntity s where s.ownerUserId = :userId and s.id = :shipId")
    Optional<ShipEntity> findByOwnerUserIdAndIdUnlocked(Long userId, Long shipId);

}