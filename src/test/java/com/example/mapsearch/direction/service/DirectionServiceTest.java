package com.example.mapsearch.direction.service;

import com.example.mapsearch.AbstractIntegrationContainerBaseTest;
import com.example.mapsearch.pharmacy.dto.PharmacyDto;
import com.example.mapsearch.pharmacy.service.PharmacySearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DirectionServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private DirectionService directionService;

    @Autowired
    private PharmacySearchService pharmacySearchService;

    private List<PharmacyDto> pharmacyList;

    @BeforeEach
    void setup() {
        pharmacyList = new ArrayList<>();

        pharmacyList.addAll(
                List.of(
                        PharmacyDto.builder()
                                .id(1L)
                                .pharmacyName("돌곶이온누리약국")
                                .pharmacyAddress("주소1")
                                .latitude(37.61040424)
                                .longitude(127.0569046)
                                .build(),
                        PharmacyDto.builder()
                                .id(2L)
                                .pharmacyName("호수온누리약국")
                                .pharmacyAddress("주소2")
                                .latitude(37.60894036)
                                .longitude(127.029052)
                                .build()
                )
        );
    }

    @Test
    void test() {

    }

}