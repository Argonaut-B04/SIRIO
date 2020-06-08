package com.argonautb04.sirio.controller;

import com.argonautb04.sirio.model.Role;
import com.argonautb04.sirio.rest.BaseResponse;
import com.argonautb04.sirio.services.RoleRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/Role")
public class RoleRestController {

    /**
     * Complete and Success Response Code.
     */
    private final int complete = 200;

    /**
     * Bind to Role Rest Service.
     */
    @Autowired
    private RoleRestService roleRestService;

    /**
     * Mengambil seluruh role.
     *
     * @return daftar role.
     */
    @GetMapping("/getAll")
    private BaseResponse<List<Role>> getAllRole() {
        return new BaseResponse<>(complete, "success", roleRestService.getAll());
    }
}
