package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.model.Employee;
import com.ArgonautB04.SIRIO.rest.BaseResponse;
import com.ArgonautB04.SIRIO.rest.EmployeeDTO;
import com.ArgonautB04.SIRIO.services.EmployeeRestService;
import com.ArgonautB04.SIRIO.services.RoleRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping("/api/v1/Employee")
public class EmployeeRestController {

    @Autowired
    private EmployeeRestService employeeRestService;

    @Autowired
    private RoleRestService roleRestService;

    @GetMapping("/login")
    public BaseResponse<EmployeeDTO> authenticate(Principal principal, ModelMap model) {
        BaseResponse<EmployeeDTO> response = new BaseResponse<>();

        Employee employee = employeeRestService.getByUsername(principal.getName());
        EmployeeDTO target = new EmployeeDTO();
        target.setId(employee.getIdEmployee());
        target.setUsername(employee.getUsername());
        target.setRole(
                employee.getRole().getNamaRole()
        );

        response.setStatus(200);
        response.setMessage("success");
        response.setResult(target);

        return response;
    }
}
