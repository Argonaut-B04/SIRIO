package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.RiskLevel;
import com.ArgonautB04.SIRIO.rest.BaseResponse;
import com.ArgonautB04.SIRIO.services.EmployeeRestService;
import com.ArgonautB04.SIRIO.services.RiskLevelRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/RiskLevel")
public class RiskLevelRestController {

    @Autowired
    private RiskLevelRestService riskLevelRestService;

    @Autowired
    private EmployeeRestService employeeRestService;

    @PostMapping("/nama")
    private BaseResponse<Boolean> isExistInDatabase(
            @RequestBody String namaRiskLevelBaru,
            Principal principal
    ) {
        Employee pengelola = employeeRestService.validateEmployeeExistByPrincipal(principal);
        employeeRestService.validateRolePermission(pengelola, "akses risk level");
        return new BaseResponse<>(200, "success", riskLevelRestService.isExistInDatabase(namaRiskLevelBaru));
    }

    @GetMapping("/Aktif")
    private BaseResponse<List<RiskLevel>> getAllAktifRiskLevel(
            Principal principal
    ) {
        Employee employee = employeeRestService.validateEmployeeExistByPrincipal(principal);
        employeeRestService.validateRolePermission(employee, "akses risk level");
        return new BaseResponse<>(200, "success", riskLevelRestService.getAktif());
    }

    @PostMapping("")
    private BaseResponse<List<RiskLevel>> changeRiskLevelConfiguration(
            @RequestBody List<RiskLevel> riskLevelList,
            Principal principal
    ) {
        Employee pengelola = employeeRestService.validateEmployeeExistByPrincipal(principal);
        employeeRestService.validateRolePermission(pengelola, "ubah risk level");

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

        return new BaseResponse<>(200, "success", newRiskLevelList);
    }
}
