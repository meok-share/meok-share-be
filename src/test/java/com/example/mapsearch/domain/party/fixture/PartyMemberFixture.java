package com.example.mapsearch.domain.party.fixture;

import com.example.mapsearch.domain.party.entity.PartyMember;

public class PartyMemberFixture {

    public static final PartyMember 멤버_1 = PartyMember.builder()
            .id(1L)
            .nickName("test1")
            .build();

    public static final PartyMember 멤버_2 = PartyMember.builder()
            .id(2L)
            .nickName("test2")
            .build();

    public static final PartyMember 멤버_3 = PartyMember.builder()
            .id(3L)
            .nickName("test3")
            .build();

    public static final PartyMember 멤버_4 = PartyMember.builder()
            .id(3L)
            .nickName("test4")
            .build();

}
