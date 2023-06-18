package com.example.mapsearch.domain.party.service;

import com.example.mapsearch.domain.party.dto.request.MemberRequest;
import com.example.mapsearch.domain.party.dto.request.PartyRequest;
import com.example.mapsearch.domain.party.entity.Party;
import com.example.mapsearch.domain.party.repository.PartyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PartyService {

    private final PartyRepository partyRepository;

    @Transactional
    public void createParty(PartyRequest req) {
        validateParty(req);
        partyRepository.save(req.toEntity());
    }

    private void validateParty(final PartyRequest req) {
        partyRepository.findByPartyName(req.getPartyName())
                .ifPresent(party -> {
                    throw new IllegalArgumentException("이미 존재하는 파티 입니다.");
                });
    }

    @Transactional
    public void updateParty(MemberRequest req) {
        final Party party = getParty(req);

        party.addMember(req.toEntity());
    }

    private Party getParty(final MemberRequest req) {
        return partyRepository.findById(req.getPartyId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 파티입니다."));
    }

}
