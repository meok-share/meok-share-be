package com.example.mapsearch.domain.party.repository;

import com.example.mapsearch.domain.party.entity.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PartyRepository extends JpaRepository<Party, Long> {

    Optional<Party> findByPartyName(String partyName);

}
