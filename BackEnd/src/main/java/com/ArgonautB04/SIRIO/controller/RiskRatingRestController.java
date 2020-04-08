package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.RiskLevel;
import com.ArgonautB04.SIRIO.model.RiskRating;
import com.ArgonautB04.SIRIO.rest.BaseResponse;
import com.ArgonautB04.SIRIO.services.EmployeeRestService;
import com.ArgonautB04.SIRIO.services.RiskRatingRestService;
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
@RequestMapping("/api/v1/RiskRating")
public class RiskRatingRestController {

    @Autowired
    private RiskRatingRestService riskRatingRestService;

    @Autowired
    private EmployeeRestService employeeRestService;

    @GetMapping("")
    private BaseResponse<List<RiskRating>> getAllAktifRiskRating() {
        BaseResponse<List<RiskRating>> response = new BaseResponse<>();
        response.setStatus(200);
        response.setMessage("success");
        response.setResult(
                riskRatingRestService.getAll()
        );
        return response;
    }

    @PostMapping("")
    private BaseResponse<List<RiskRating>> changeRiskLevelConfiguration(
            @RequestBody List<RiskRating> riskRatingList,
            Principal principal
    ) {
        BaseResponse<List<RiskRating>> response = new BaseResponse<>();
        try {
            Optional<Employee> pengelolaOptional = employeeRestService.getByUsername(principal.getName());
            Employee pengelola;
            if (pengelolaOptional.isPresent()) {
                pengelola = pengelolaOptional.get();
            } else {
                throw new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Role Manajer diperlukan untuk mengubah konfigurasi risk rating"
                );
            }

            List<RiskRating> currentRiskRating = riskRatingRestService.getAll();
            for (RiskRating riskRating : currentRiskRating) {
                riskRatingRestService.hapusRiskRating(riskRating.getIdRating());
            }

            for (RiskRating riskRating : riskRatingList) {
                riskRating.setPengelola(pengelola);
                riskRatingRestService.buatRiskRating(riskRating);
            }


            response.setStatus(200);
            response.setMessage("success");
            response.setResult(
                    riskRatingRestService.getAll()
            );
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Konfigurasi Risk Level gagal"
            );
        }
        return response;
    }
}
