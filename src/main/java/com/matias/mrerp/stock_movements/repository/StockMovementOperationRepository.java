package com.matias.mrerp.stock_movements.repository;

import com.matias.mrerp.stock_movements.entity.StockMovementOperation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StockMovementOperationRepository
        extends JpaRepository<StockMovementOperation, UUID> {
}