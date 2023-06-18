package com.example.mapsearch.domain.party.entity;

import com.example.mapsearch.common.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Party extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private PartyMembers partyMembers = new PartyMembers();

    private String hostNickName;

    private String longitude;

    private String latitude;

    private String partyName;

    private String partyComment;

    private String partyDate;

    private int partySize;

    private int currentPartySize;

    @Builder
    public Party(final Long id,
                 final PartyMembers partyMembers,
                 final String hostNickName,
                 final String longitude,
                 final String latitude,
                 final String partyName,
                 final String partyComment,
                 final String partyDate,
                 final int partySize) {
        this.id = id;
        this.partyMembers = partyMembers;
        this.hostNickName = hostNickName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.partyName = partyName;
        this.partyComment = partyComment;
        this.partyDate = partyDate;
        this.partySize = partySize;
        this.currentPartySize = getCurrentPartySize();
    }

    public void addPartyMember(final PartyMember member) {
        this.partyMembers.add(member, partySize);
        this.currentPartySize = getCurrentPartySize();
    }

    public void removePartyMember(final PartyMember member) {
        this.partyMembers.remove(member);
        this.currentPartySize = getCurrentPartySize();
    }

    public int getCurrentPartySize() {
        return partyMembers.size();
    }

    public boolean isFullMember() {
        return getCurrentPartySize() == partySize;
    }

    public List<PartyMember> getPartyMemberAll() {
        return partyMembers.getPartyMembers();
    }

}
