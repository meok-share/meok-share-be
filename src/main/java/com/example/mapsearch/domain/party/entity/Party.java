package com.example.mapsearch.domain.party.entity;

import com.example.mapsearch.common.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Party extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "party_member_id")
    private List<PartyMember> partyMembers;

    private String hostNickName;

    private String longitude;

    private String latitude;

    private String partyName;

    private String partyComment;

    private String partyDate;

    private int partySize;

    private boolean isPartyEnd;

    @Builder
    public Party(final Long id,
                 final List<PartyMember> partyMembers,
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
    }

    public void addPartyMember(final PartyMember member) {
        validate(member);
        this.partyMembers.add(member);
    }

    private void validate(final PartyMember member) {
        if (isFullMember()) {
            throw new IllegalArgumentException("파티 인원이 가득 찼습니다.");
        }

        boolean nicknameExists = this.partyMembers.stream()
                .map(PartyMember::getNickName)
                .anyMatch(nickName -> nickName.equals(member.getNickName()));

        if (nicknameExists) {
            throw new IllegalArgumentException("이미 가입된 멤버 입니다.");
        }
    }

    public boolean isFullMember() {
        return this.partyMembers.size() == this.partySize;
    }

}
