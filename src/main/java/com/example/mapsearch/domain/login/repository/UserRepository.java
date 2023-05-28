package com.example.mapsearch.domain.login.repository;

import com.example.mapsearch.domain.login.entity.MUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<MUsers, Long> {
    Optional<MUsers> findUserByEmail(String username);

}
