package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.RiskLevel;
import com.ArgonautB04.SIRIO.rest.BaseResponse;
import com.ArgonautB04.SIRIO.services.EmployeeRestService;
import com.ArgonautB04.SIRIO.services.RiskLevelRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/RiskLevel")
public class RiskLevelRestController {

    /**
     * Complete and Success Response Code.
     */
    private final int complete = 200;

    /**
     * Bind to Risk Level Rest Service.
     */
    @Autowired
    private RiskLevelRestService riskLevelRestService;

    /**
     * Bind to Employee Rest Service.
     */
    @Autowired
    private EmployeeRestService employeeRestService;

    /**
     * Check if Risk Level name already Exist in Database.
     *
     * @param namaRiskLevelBaru name to check
     * @param principal         object from Spring Security
     * @return boolean in BaseResponse object
     */
    @PostMapping("/nama")
    private BaseResponse<Boolean> isExistInDatabase(
            final @RequestBody String namaRiskLevelBaru,
            final Principal principal
    ) {
        Employee pengelola = employeeRestService
                .validateEmployeeExistByPrincipal(principal);
        employeeRestService
                .validateRolePermission(pengelola, "akses risk level");
        return new BaseResponse<>(
                complete,
                "success",
                riskLevelRestService.isExistInDatabase(namaRiskLevelBaru)
        );
    }

    /**
     * Get all active Risk Level.
     *
     * @param principal object from Spring Security
     * @return List of active Risk Level
     */
    @GetMapping("/Aktif")
    private BaseResponse<List<RiskLevel>> getAllAktifRiskLevel(
            final Principal principal
    ) {
        Employee employee = employeeRestService
                .validateEmployeeExistByPrincipal(principal);
        employeeRestService
                .validateRolePermission(employee, "akses risk level");
        return new BaseResponse<>(
                complete,
                "success",
                riskLevelRestService.getAktif());
    }

    @PostMapping("")
    private BaseResponse<List<RiskLevel>> changeRiskLevelConfiguration(
            final @RequestBody List<RiskLevel> riskLevelList,
            final Principal principal
    ) {
        Employee pengelola = employeeRestService
                .validateEmployeeExistByPrincipal(principal);
        employeeRestService
                .validateRolePermission(pengelola, "ubah risk level");

        List<RiskLevel> currentRiskLevel = riskLevelRestService.getAktif();
        List<RiskLevel> newRiskLevelList = new ArrayList<>();

        for (RiskLevel riskLevel : riskLevelList) {
            if (riskLevelRestService.isExistInDatabase(riskLevel)) {
                newRiskLevelList.add(
                        riskLevelRestService
                                .ubahRiskLevel(
                                        riskLevel.getIdLevel(),
                                        riskLevel
                                )
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

        return new BaseResponse<>(complete, "success", newRiskLevelList);
    }
}
