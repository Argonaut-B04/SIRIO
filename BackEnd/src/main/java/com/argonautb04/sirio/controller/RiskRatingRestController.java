package com.argonautb04.sirio.controller;

import com.argonautb04.sirio.model.Employee;
import com.argonautb04.sirio.model.RiskRating;
import com.argonautb04.sirio.rest.BaseResponse;
import com.argonautb04.sirio.services.EmployeeRestService;
import com.argonautb04.sirio.services.KantorCabangRestService;
import com.argonautb04.sirio.services.RiskRatingRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/RiskRating")
public class RiskRatingRestController {

    /**
     * Complete and Success Response Code.
     */
    private final int complete = 200;

    /**
     * Bind to Risk Rating Rest Service.
     */
    @Autowired
    private RiskRatingRestService riskRatingRestService;

    /**
     * Bind to Employee Rest Service.
     */
    @Autowired
    private EmployeeRestService employeeRestService;

    /**
     * Bind to Branch Office Rest Service.
     */
    @Autowired
    private KantorCabangRestService kantorCabangRestService;

    /**
     * Get all active Risk Rating.
     *
     * @param principal object from Spring Security
     * @return List of active Risk Rating
     */
    @GetMapping("")
    private BaseResponse<List<RiskRating>> getAllAktifRiskRating(final Principal principal) {
        Employee pengelola = employeeRestService.validateEmployeeExistByPrincipal(principal);
        employeeRestService.validateRolePermission(pengelola, "akses risk rating");
        return new BaseResponse<>(complete, "success", riskRatingRestService.getAll());
    }

    /**
     * Change risk level configuration.
     *
     * @param riskRatingList List of new Risk Rating
     * @param principal      object from Spring Security
     * @return List of new registered Risk Rating
     */
    @PostMapping("")
    private BaseResponse<List<RiskRating>> changeRiskLevelConfiguration(
            final @RequestBody List<RiskRating> riskRatingList, final Principal principal) {
        Employee pengelola = employeeRestService.validateEmployeeExistByPrincipal(principal);
        employeeRestService.validateRolePermission(pengelola, "ubah risk rating");

        kantorCabangRestService.nullifiedRiskRating();
        riskRatingRestService.clear();

        for (RiskRating riskRating : riskRatingList) {
            riskRating.setPengelola(pengelola);
            riskRatingRestService.buatRiskRating(riskRating);
        }

        kantorCabangRestService.recalculateRiskRating();

        return new BaseResponse<>(complete, "success", riskRatingRestService.getAll());

    }
}
