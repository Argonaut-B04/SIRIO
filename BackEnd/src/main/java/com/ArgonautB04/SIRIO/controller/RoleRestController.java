package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.model.Role;
import com.ArgonautB04.SIRIO.rest.BaseResponse;
import com.ArgonautB04.SIRIO.services.RoleRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/Role")
public class RoleRestController {

    @Autowired
    private RoleRestService roleRestService;

    /**
     * Mengambil seluruh role
     *
     * @return daftar role
     */
    @GetMapping("/getAll")
    private BaseResponse<List<Role>> getAllRole() {
        BaseResponse<List<Role>> response = new BaseResponse<>();
        List<Role> result = roleRestService.getAll();
        response.setStatus(200);
        response.setMessage("success");
        response.setResult(result);
        return response;
    }

}
