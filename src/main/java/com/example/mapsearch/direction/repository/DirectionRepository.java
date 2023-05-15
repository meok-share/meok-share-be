package com.example.mapsearch.direction.repository;

import com.example.mapsearch.direction.entity.Direction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectionRepository extends JpaRepository<Direction, Long> {
}
