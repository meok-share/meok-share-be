package com.example.mapsearch.pharmacy.service;


import com.example.mapsearch.pharmacy.dto.PharmacyDto;
import com.example.mapsearch.pharmacy.entity.Pharmacy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PharmacySearchService {

    private final PharmacyRepositoryService pharmacyRepositoryService;

    public List<PharmacyDto> searchPharmacyList() {
        final List<Pharmacy> pharmacyList = pharmacyRepositoryService.findAll();

        return pharmacyList.stream()
                .map(PharmacyDto::of)
                .collect(Collectors.toList());
    }

}
