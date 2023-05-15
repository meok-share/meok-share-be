package com.example.mapsearch.pharmacy.repository;

import com.example.mapsearch.AbstractIntegrationContainerBaseTest;
import com.example.mapsearch.pharmacy.entity.Pharmacy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PharmacyRepositoryTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private PharmacyRepository pharmacyRepository;

    @Test
    void setup() {
        pharmacyRepository.deleteAll();
    }

    @Test
    void testSavePharmacy() {
        // Given
        Pharmacy pharmacy = Pharmacy.builder()
                .pharmacyName("Test Pharmacy")
                .pharmacyAddress("123 Main St")
                .latitude(40.7128)
                .longitude(74.0060)
                .build();

        // When
        pharmacyRepository.save(pharmacy);

        // Then
        Pharmacy savedPharmacy = pharmacyRepository.findById(pharmacy.getId()).orElse(null);
        assertThat(savedPharmacy.getPharmacyName()).isEqualTo("Test Pharmacy");
    }


    @Test
    void TestSaveAll() {
        // Given
        Pharmacy pharmacy1 = Pharmacy.builder()
                .pharmacyName("Test Pharmacy 1")
                .pharmacyAddress("123 Main St")
                .latitude(40.7128)
                .longitude(74.0060)
                .build();
        Pharmacy pharmacy2 = Pharmacy.builder()
                .pharmacyName("Test Pharmacy 2")
                .pharmacyAddress("123 Main St")
                .latitude(40.7128)
                .longitude(74.0060)
                .build();
        Pharmacy pharmacy3 = Pharmacy.builder()
                .pharmacyName("Test Pharmacy 3")
                .pharmacyAddress("123 Main St")
                .latitude(40.7128)
                .longitude(74.0060)
                .build();

        // When
        pharmacyRepository.saveAll(List.of(pharmacy1, pharmacy2, pharmacy3));

        // Then
        List<Pharmacy> savedPharmacies = pharmacyRepository.findAll();
        assertThat(savedPharmacies.size()).isEqualTo(3);
    }
}
