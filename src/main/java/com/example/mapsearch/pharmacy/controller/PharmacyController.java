package com.example.mapsearch.pharmacy.controller;

import com.example.mapsearch.pharmacy.controller.request.DirectionReq;
import com.example.mapsearch.pharmacy.controller.response.DirectionRes;
import com.example.mapsearch.pharmacy.entity.Pharmacy;
import com.example.mapsearch.pharmacy.service.PharmacyRecommendationService;
import com.example.mapsearch.pharmacy.service.PharmacyRepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RestController
@RequestMapping("/pharmacy")
@RequiredArgsConstructor
public class PharmacyController {

    private final PharmacyRepositoryService pharmacyRepositoryService;

    private final PharmacyRecommendationService recommendPharmacyService;


    @GetMapping("/recommend")
    public List<DirectionRes> getRecommendPharmacyList(@ModelAttribute DirectionReq req) {
        return recommendPharmacyService.recommendPharmacy(req.getAddress());
    }

    @GetMapping("/list")
    public List<Pharmacy> getPharmacyList() {
        return pharmacyRepositoryService.findAll();
    }

}
