package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.services.BuktiPelaksanaanRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/BuktiPelaksanaan")
public class BuktiPelaksanaanRestController {

    @Autowired
    private BuktiPelaksanaanRestService buktiPelaksanaanRestService;

}
