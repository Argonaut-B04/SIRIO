package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.model.RiskLevel;
import com.ArgonautB04.SIRIO.rest.BaseResponse;
import com.ArgonautB04.SIRIO.services.RiskLevelRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/RiskLevel")
public class RiskLevelRestController {

    @Autowired
    private RiskLevelRestService riskLevelRestService;

    @GetMapping("/Aktif")
    private BaseResponse<List<RiskLevel>> getAllAktifRiskLevel() {
        BaseResponse<List<RiskLevel>> response = new BaseResponse<>();
        response.setStatus(200);
        response.setMessage("success");
        response.setResult(
                riskLevelRestService.getAktif()
        );
        return response;
    }

}
