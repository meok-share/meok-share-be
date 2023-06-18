package com.example.mapsearch.domain.party.entity;

import lombok.Getter;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Embeddable
public class PartyMembers {

    @OneToMany(mappedBy = "party", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<PartyMember> partyMembers = new ArrayList<>();

    public void add(final PartyMember member, final int size) {
        this.validate(member, size);
        partyMembers.add(member);
    }

    private void validate(final PartyMember member, final int size) {
        if (isFullMember(size)) {
            throw new IllegalArgumentException("파티 인원이 가득 찼습니다.");
        }

        boolean nicknameExists = this.partyMembers.stream()
                .map(PartyMember::getNickName)
                .anyMatch(nickName -> nickName.equals(member.getNickName()));

        if (nicknameExists) {
            throw new IllegalArgumentException("이미 가입된 멤버 입니다.");
        }
    }

    public boolean isFullMember(final int size) {
        return this.partyMembers.size() == size;
    }

    public void remove(PartyMember member) {
        partyMembers.remove(member);
    }

    public int size() {
        return partyMembers.size();
    }

}