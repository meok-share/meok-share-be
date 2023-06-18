package com.example.mapsearch.domain.party.controller;

import com.example.mapsearch.domain.party.dto.request.MemberRequest;
import com.example.mapsearch.domain.party.dto.request.PartyRequest;
import com.example.mapsearch.domain.party.service.PartyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/party")
public class PartyController {

    private final PartyService partyService;

    @PostMapping("/create")
    public void createParty(@RequestBody PartyRequest req) {
        partyService.createParty(req);
    }

    @PostMapping("/update-member")
    public void addMember(@RequestBody MemberRequest req) {
        partyService.updateParty(req);
    }

}
