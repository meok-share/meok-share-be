package com.example.mapsearch.direction.controller;

import com.example.mapsearch.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.util.UriComponentsBuilder;

import static com.example.mapsearch.common.constants.URL.KakaoUrl.DIRECTION_BASE_URL;

@Controller
@RequiredArgsConstructor
public class DirectionController {

    private final DirectionService directionService;

    @GetMapping("/dir/{encodedId}")
    public String searchDirection(@PathVariable("encodedId") String encodedId) {
        final String byId = directionService.findDirectionUrlById(encodedId);

        return UriComponentsBuilder
                .fromHttpUrl(DIRECTION_BASE_URL + byId)
                .toUriString();
    }

}
