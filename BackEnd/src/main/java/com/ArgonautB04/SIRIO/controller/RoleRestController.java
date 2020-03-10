package com.ArgonautB04.SIRIO.controller;

import com.ArgonautB04.SIRIO.services.RoleRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/Role")
public class RoleRestController {

    @Autowired
    private RoleRestService roleRestService;

}
