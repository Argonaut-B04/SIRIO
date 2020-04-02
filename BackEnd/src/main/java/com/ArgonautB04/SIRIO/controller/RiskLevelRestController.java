package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.RiskLevel;
import com.ArgonautB04.SIRIO.rest.BaseResponse;
import com.ArgonautB04.SIRIO.services.EmployeeRestService;
import com.ArgonautB04.SIRIO.services.RiskLevelRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/RiskLevel")
public class RiskLevelRestController {

    @Autowired
    private RiskLevelRestService riskLevelRestService;

    @Autowired
    private EmployeeRestService employeeRestService;

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

    @PostMapping("")
    private BaseResponse<List<RiskLevel>> changeRiskLevelConfiguration(
            @RequestBody List<RiskLevel> riskLevelList,
            Principal principal
    ) {
        BaseResponse<List<RiskLevel>> response = new BaseResponse<>();
        try {
            Optional<Employee> pengelolaOptional = employeeRestService.getByUsername(principal.getName());
            Employee pengelola;
            if (pengelolaOptional.isPresent()) {
                pengelola = pengelolaOptional.get();
            } else {
                throw new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Role Manajer diperlukan untuk mengubah konfigurasi risk level"
                );
            }

            List<RiskLevel> currentRiskLevel = riskLevelRestService.getAktif();
            List<RiskLevel> newRiskLevelList = new ArrayList<>();

            for (RiskLevel riskLevel : riskLevelList) {
                if (riskLevelRestService.isExistInDatabase(riskLevel)) {
                    newRiskLevelList.add(
                            riskLevelRestService.ubahRiskLevel(riskLevel.getIdLevel(), riskLevel)
                    );
                } else {
                    riskLevel.setPengelola(pengelola);
                    newRiskLevelList.add(
                            riskLevelRestService.buatRiskLevel(riskLevel)
                    );
                }
            }

            currentRiskLevel.removeAll(newRiskLevelList);

            for (RiskLevel riskLevel : currentRiskLevel) {
                riskLevelRestService.nonaktifkan(riskLevel);
            }

            response.setStatus(200);
            response.setMessage("success");
            response.setResult(newRiskLevelList);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Konfigurasi Risk Level gagal"
            );
        }
        return response;
    }
}
