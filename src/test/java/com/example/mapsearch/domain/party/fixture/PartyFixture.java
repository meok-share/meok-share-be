package com.example.mapsearch.domain.party.fixture;

import com.example.mapsearch.domain.party.entity.Party;
import com.example.mapsearch.domain.party.entity.PartyMembers;

public class PartyFixture {

    public static final Party 인사동_파티 = Party.builder()
            .id(1L)
            .partyMembers(new PartyMembers())
            .partyComment("점심 같이 먹자")
            .partyName("인사동 젊음의 거리 탐방")
            .hostNickName("홍길동")
            .partySize(3)
            .build();

}
