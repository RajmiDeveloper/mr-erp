package com.matias.mrerp.stock_movements.repository;

import com.matias.mrerp.stock_movements.entity.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StockMovementRepository extends JpaRepository<StockMovement, UUID> {
}
