package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.services.StatusBuktiPelaksanaanRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/StatusBuktiPelaksanaan")
public class StatusBuktiPelaksanaanRestController {

    @Autowired
    private StatusBuktiPelaksanaanRestService statusBuktiPelaksanaanRestService;

}
