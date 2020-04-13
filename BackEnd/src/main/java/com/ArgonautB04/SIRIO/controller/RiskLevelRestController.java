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
    private BaseResponse<List<RiskLevel>> getAllAktifRiskLevel(
            Principal principal
    ) {
        BaseResponse<List<RiskLevel>> response = new BaseResponse<>();
        Optional<Employee> pengelolaOptional = employeeRestService.getByUsername(principal.getName());
        Employee pengelola;
        if (pengelolaOptional.isPresent()) {
            pengelola = pengelolaOptional.get();
            if (pengelola.getRole().getAccessPermissions().getAksesRiskLevel()) {
                response.setStatus(200);
                response.setMessage("success");
                response.setResult(
                        riskLevelRestService.getAktif()
                );
            } else throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Akun anda tidak memiliki akses ke pengaturan ini"
            );
        } else throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED, "Akun anda tidak terdaftar dalam Sirio"
        );
        return response;
    }

    @PostMapping("")
    private BaseResponse<List<RiskLevel>> changeRiskLevelConfiguration(
            @RequestBody List<RiskLevel> riskLevelList,
            Principal principal
    ) {
        BaseResponse<List<RiskLevel>> response = new BaseResponse<>();
        Optional<Employee> pengelolaOptional = employeeRestService.getByUsername(principal.getName());
        Employee pengelola;
        if (pengelolaOptional.isPresent()) {
            pengelola = pengelolaOptional.get();
            if (pengelola.getRole().getAccessPermissions().getUbahRiskLevel()) {

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
                return response;

            } else throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Akun anda tidak memiliki akses ke pengaturan ini"
            );
        } else throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED, "Akun anda tidak terdaftar dalam Sirio"
        );
    }
}
