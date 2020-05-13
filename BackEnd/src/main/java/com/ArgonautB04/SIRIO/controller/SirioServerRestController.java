package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.rest.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class SirioServerRestController {

    @GetMapping("/poll")
    private BaseResponse<String> pollServer() {
        return new BaseResponse<>(200, "success", "Berhasil terhubung ke server");
    }

}
