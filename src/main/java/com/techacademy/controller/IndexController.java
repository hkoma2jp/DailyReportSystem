package com.techacademy.controller;

import org.springframework.stereotype.Controller;

@Controller
public class IndexController {

    @GetMapping("/")
    public String getIndex() {
        return "index";
    }
}
