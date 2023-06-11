package com.techacademy.controller;

import java.time.LocalDateTime;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techacademy.entity.Employee;
import com.techacademy.entity.Report;
import com.techacademy.service.EmployeeService;
import com.techacademy.service.ReportService;
import com.techacademy.service.UserDetail;

@Controller
@RequestMapping("report")
public class ReportController {
    private final ReportService service;

    public ReportController(ReportService service) {
        this.service = service;
    }

    /** 記事一覧画面を表示 */
    @GetMapping("/list")
    public String getList(Model model) {
        model.addAttribute("reportList", service.getReportList());
        return "report/list";
    }

    /** 詳細画面を表示 */
    @GetMapping("/{id}")
    public String getReport(@PathVariable("id") Integer id, Model model, @AuthenticationPrincipal UserDetail authinfo) {
        model.addAttribute("authinfo", authinfo.getUser());
        model.addAttribute("report", service.getReport(id));
        return "report/detail";
    }

    /** 新規登録画面を表示 */
    @GetMapping("/new")
    public String getRegister(@ModelAttribute Report report) {
        return "report/register";
    }

    /** 新規登録処理 */
    @PostMapping("/register")
    public String postRegister(@Validated Report report, BindingResult res, @AuthenticationPrincipal UserDetail authinfo) {
        if(res.hasErrors()) {
            // エラーあり
            return getRegister(report);
        }
        // 1. ユーザーをセット（現在ログイン中のユーザー情報をセット）
        report.setEmployee(authinfo.getUser());
        // 2. 登録日時をセット（現在日時をセット）
        report.setCreated_at(LocalDateTime.now());
        // 3. 更新日時をセット（現在日時をセット）
        report.setUpdated_at(LocalDateTime.now());
        // 4. DBへ登録する
        service.saveReport(report);
        return "redirect:/report/list";
    }

    /** 編集画面を表示 */
    @GetMapping("/edit/{id}")
    public String getEdit(@PathVariable("id") Integer id, Report report, Model model) {
        if (id != null ) {
            report = service.getReport(id);
        }
        model.addAttribute("report", report);
        return "report/edit";
    }

    /** 更新処理 */
    @PostMapping("/update/{id}")
    public String postReport(@PathVariable("id") Integer id, @Validated Report report, BindingResult res, Model model) {
        if (res.hasErrors()) {
            return "report/edit";
        }
        // 0. DBからreportを取得
        Report reptDb = service.getReport(id);
        // 1. ユーザーをセット（DBのreportのemployeeをセット）
        report.setEmployee(reptDb.getEmployee());
        // 2. 登録日時をセット（DBのreportの登録日時をセット）
        report.setCreated_at(reptDb.getCreated_at());
        // 3. 更新日時をセット（現在日時をセット）
        report.setUpdated_at(LocalDateTime.now());
        // 4. DBへ登録する
        service.saveReport(report);
        return "redirect:/report/list";
    }
}









