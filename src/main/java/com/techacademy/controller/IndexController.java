package com.techacademy.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techacademy.entity.Employee;
import com.techacademy.entity.Report;
import com.techacademy.service.ReportService;
import com.techacademy.service.UserDetail;

@Controller
@RequestMapping("/")
public class IndexController {
    private final ReportService service;

    public IndexController(ReportService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String getIndex(Model model, @AuthenticationPrincipal UserDetail userauth) {
        model.addAttribute("reportList", service.getEmployeeReportList(userauth.getUser()));
        return "index";
    }
}
