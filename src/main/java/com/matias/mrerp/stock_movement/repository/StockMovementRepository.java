package com.matias.mrerp.stock_movement.repository;

import com.matias.mrerp.stock_movement.entity.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StockMovementRepository extends JpaRepository<StockMovement, UUID> {
}
