package com.argonautb04.sirio.controller;

import com.argonautb04.sirio.services.StatusRekomendasiRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/StatusRekomendasi")
public class StatusRekomendasiRestController {

    @Autowired
    private StatusRekomendasiRestService statusRekomendasiRestService;

}
