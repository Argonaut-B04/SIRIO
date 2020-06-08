package com.argonautb04.sirio.controller;

import com.argonautb04.sirio.rest.BaseResponse;
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
