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

    /** 新規登録画面を表示 */
    @GetMapping("/new")
    public String getRegister(@ModelAttribute Employee employee) {
        return "employee/register";
    }

    /** 新規登録処理 */
    @PostMapping("/register")
    public String postRegister(@Validated Employee employee, BindingResult res ) {
        if(res.hasErrors()) {
            // エラーあり
            return getRegister(employee);
        }
        // 削除フラグは0
        employee.setDelete_flag(0);
        // 登録日時＝更新日時
        employee.setCreated_at(LocalDateTime.now());
        employee.setUpdated_at(LocalDateTime.now());

        service.saveEmployee(employee);
        return "redirect:/employee/list";
    }

    /** 編集画面を表示 */
    @GetMapping("/edit/{id}")
    public String getEdit(@PathVariable("id") Integer id, Employee employee, Model model) {
        if (id != null ) {
            employee = service.getEmployee(id);
        }
        model.addAttribute("employee", employee);
        return "employee/edit";
    }

    /** 更新処理 */
    @PostMapping("/update/{id}")
    public String postEmployee(@PathVariable("id") Integer id, Employee employee, BindingResult res, Model model ) {
        if (res.hasErrors()) {
            return getEdit(id, employee, model);
        }
        // 削除フラグは0
        employee.setDelete_flag(0);
        // 登録日時はそのまま
        LocalDateTime createdAt = service.getEmployee(id).getCreated_at();
        employee.setCreated_at(createdAt);
        // 更新日時は更新
        employee.setUpdated_at(LocalDateTime.now());

        service.saveEmployee(employee);
        return "redirect:/employee/list";
    }
}
