package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.model.Role;
import com.ArgonautB04.SIRIO.model.SOP;
import com.ArgonautB04.SIRIO.rest.BaseResponse;
import com.ArgonautB04.SIRIO.services.RoleRestService;
import com.ArgonautB04.SIRIO.services.SOPRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/SOP")
public class SOPRestController {

    @Autowired
    private SOPRestService sopRestService;

    /**
     * Mengambil seluruh sop
     *
     * @return daftar sop
     */
    @GetMapping("/getAll")
    private BaseResponse<List<SOP>> getAllSop() {
        BaseResponse<List<SOP>> response = new BaseResponse<>();
        List<SOP> result = sopRestService.getAll();
        response.setStatus(200);
        response.setMessage("success");
        response.setResult(result);
        return response;
    }

}
