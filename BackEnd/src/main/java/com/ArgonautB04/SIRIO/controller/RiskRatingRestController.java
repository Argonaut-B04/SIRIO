package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.model.RiskRating;
import com.ArgonautB04.SIRIO.rest.BaseResponse;
import com.ArgonautB04.SIRIO.services.EmployeeRestService;
import com.ArgonautB04.SIRIO.services.KantorCabangRestService;
import com.ArgonautB04.SIRIO.services.RiskRatingRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/RiskRating")
public class RiskRatingRestController {

    @Autowired
    private RiskRatingRestService riskRatingRestService;

    @Autowired
    private EmployeeRestService employeeRestService;

    @Autowired
    private KantorCabangRestService kantorCabangRestService;

    @GetMapping("")
    private BaseResponse<List<RiskRating>> getAllAktifRiskRating(
            Principal principal
    ) {
        Employee pengelola = employeeRestService.validateEmployeeExistByPrincipal(principal);
        employeeRestService.validateRolePermission(pengelola, "akses risk rating");
        return new BaseResponse<>(200, "success", riskRatingRestService.getAll());
    }

    @PostMapping("")
    private BaseResponse<List<RiskRating>> changeRiskLevelConfiguration(
            @RequestBody List<RiskRating> riskRatingList,
            Principal principal
    ) {
        Employee pengelola = employeeRestService.validateEmployeeExistByPrincipal(principal);
        employeeRestService.validateRolePermission(pengelola, "ubah risk rating");

        kantorCabangRestService.nullifiedRiskRating();
        riskRatingRestService.clear();

        for (RiskRating riskRating : riskRatingList) {
            riskRating.setPengelola(pengelola);
            riskRatingRestService.buatRiskRating(riskRating);
        }

        kantorCabangRestService.recalculateRiskRating();

        return new BaseResponse<>(200, "success", riskRatingRestService.getAll());

    }
}
