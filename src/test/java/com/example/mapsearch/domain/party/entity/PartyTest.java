package com.example.mapsearch.domain.party.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.mapsearch.domain.party.fixture.PartyFixture.인사동_파티;
import static com.example.mapsearch.domain.party.fixture.PartyMemberFixture.멤버_1;
import static com.example.mapsearch.domain.party.fixture.PartyMemberFixture.멤버_2;
import static com.example.mapsearch.domain.party.fixture.PartyMemberFixture.멤버_3;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Party")
class PartyTest {

    @Test
    @DisplayName("파티 정원을 추가한다.")
    void testAddParty() {
        // Given

        인사동_파티.addPartyMember(멤버_1);
        인사동_파티.addPartyMember(멤버_2);

        // When & Then
        assertThat(인사동_파티.getCurrentPartySize()).isEqualTo(2);
    }

    @Test
    @DisplayName("파티를 이미 가입한 사람은 예외를 반환한다.")
    void addPartyMemberThrowException() {
        // Given
        인사동_파티.addPartyMember(멤버_1);

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> 인사동_파티.addPartyMember(멤버_1));
    }

    @Test
    @DisplayName("파티 정원이 꽉 차지 않았을 때 false 를 반환한다.")
    void partyFullMember() {
        // Given
        인사동_파티.addPartyMember(멤버_1);
        인사동_파티.addPartyMember(멤버_2);

        // When & Then
        assertThat(인사동_파티.isFullMember()).isFalse();
    }

    @Test
    @DisplayName("파티 정원이 꽉 찼을 때 예외를 반환한다.")
    void partyFullMemberThrowException() {
        // Given
        인사동_파티.addPartyMember(멤버_1);
        인사동_파티.addPartyMember(멤버_2);
        인사동_파티.addPartyMember(멤버_3);

        // When & Then
        assertThat(인사동_파티.isFullMember()).isTrue();
    }

    @Test
    @DisplayName("파티를 나가면 정원이 차감된다.")
    void removePartyMember() {
        // Given
        인사동_파티.addPartyMember(멤버_1);
        인사동_파티.addPartyMember(멤버_2);
        인사동_파티.addPartyMember(멤버_3);

        // When
        인사동_파티.removePartyMember(멤버_1);

        // Then
        assertThat(인사동_파티.getCurrentPartySize()).isEqualTo(2);
        assertThat(인사동_파티.isFullMember()).isFalse();

        System.out.println("party = " + 인사동_파티.getPartyMemberAll());
    }


}