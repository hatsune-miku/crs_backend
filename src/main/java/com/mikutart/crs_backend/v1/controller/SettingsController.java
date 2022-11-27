package com.mikutart.crs_backend.v1.controller;

import com.mikutart.crs_backend.v1.controller.generic.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SettingsController extends BaseController {
    @GetMapping
    public String getSettings() {
        return "settings";
    }
}
