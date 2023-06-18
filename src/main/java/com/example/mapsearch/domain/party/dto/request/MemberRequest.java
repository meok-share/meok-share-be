package com.example.mapsearch.domain.party.dto.request;

import com.example.mapsearch.domain.party.entity.PartyMember;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberRequest {

    private String nickName;

    private Long partyId;

    @Builder
    public PartyMember toEntity() {
        return PartyMember.builder()
                .nickName(nickName)
                .build();
    }
}
