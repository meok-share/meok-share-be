package com.example.mapsearch.domain.party.dto.request;

import com.example.mapsearch.domain.party.entity.Party;
import lombok.Getter;

@Getter
public class PartyRequest {

    private String hostNickName;

    private String longitude;

    private String latitude;

    private String partyName;

    private String partyComment;

    private String partyDate;

    private int partySize;

    public Party toEntity() {
        return Party.builder()
                .hostNickName(hostNickName)
                .longitude(longitude)
                .latitude(latitude)
                .partyName(partyName)
                .partyComment(partyComment)
                .partyDate(partyDate)
                .partySize(partySize)
                .build();
    }

}
