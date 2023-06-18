package com.example.mapsearch.domain.party.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Party")
class PartyTest {

    private static final PartyMember member1 = PartyMember.builder()
            .id(1L)
            .nickName("test1")
            .build();

    private static final PartyMember member2 = PartyMember.builder()
            .id(2L)
            .nickName("test2")
            .build();

    private static final PartyMember member3 = PartyMember.builder()
            .id(3L)
            .nickName("test3")
            .build();

    private final Party party = Party.builder()
            .id(1L)
            .partyMembers(new ArrayList<>())
            .partySize(3)
            .build();

    @Test
    @DisplayName("파티 정원을 추가한다.")
    void testAddParty() {
        // Given
        party.addPartyMember(member1);
        party.addPartyMember(member2);

        // When & Then
        assertThat(party.getPartyMembers()).hasSize(2);
    }

    @Test
    @DisplayName("파티를 이미 가입한 사람은 예외를 반환한다.")
    void addPartyMemberThrowException() {
        // Given
        party.addPartyMember(member1);

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> party.addPartyMember(member1));
    }

    @Test
    @DisplayName("파티 정원이 꽉 차지 않았을 때 false 를 반환한다.")
    void partyFullMember() {
        // Given
        party.addPartyMember(member1);
        party.addPartyMember(member2);

        // When & Then
        assertThat(party.isFullMember()).isFalse();
    }

    @Test
    @DisplayName("파티 정원이 꽉 찼을 때 예외를 반환한다.")
    void partyFullMemberThrowException() {
        // Given
        party.addPartyMember(member1);
        party.addPartyMember(member2);
        party.addPartyMember(member3);

        // When & Then
        assertThat(party.isFullMember()).isTrue();
    }

}