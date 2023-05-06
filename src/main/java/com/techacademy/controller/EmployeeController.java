package com.techacademy.controller;

import java.time.LocalDateTime;
import java.util.List;

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
import com.techacademy.service.EmployeeService;

@Controller
@RequestMapping("employee")
public class EmployeeController {
    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    /** 一覧画面を表示 */
    @GetMapping("/list")
    public String getList(Model model) {
        model.addAttribute("employeeList", service.getEmployeeList());
        return "employee/list";
    }

    /** 詳細画面を表示 */
    @GetMapping("/{id}")
    public String getEmployee(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("employee", service.getEmployee(id));
        return "employee/detail";
    }

    /** */
    @GetMapping("/new")
    public String getRegister(@ModelAttribute Employee employee) {
        return "employee/register";
    }

    @PostMapping("/register")
    public String postRegister(@Validated Employee employee, BindingResult res ) {
        if(res.hasErrors()) {
            // エラーあり
            return getRegister(employee);
        }

        employee.setDelete_flag(0);
        // 登録日時＝更新日時
        employee.setCreated_at(LocalDateTime.now());
        employee.setUpdated_at(LocalDateTime.now());

        service.saveEmployee(employee);
        return "redirect:/employee/list";
    }
}
