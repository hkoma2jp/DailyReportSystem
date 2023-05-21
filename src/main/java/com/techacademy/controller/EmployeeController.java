package com.techacademy.controller;

import java.time.LocalDateTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.techacademy.entity.Authentication;
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
        // Formから取得したemployeeに対して、削除フラグと日時を入れて、DBに書込
        Authentication auth = employee.getAuthentication();
        // 1. Form の authentication に employee を登録 (employeeとauthenticationは OneToOne)
        auth.setEmployee(employee);
        // 2. 削除フラグは0
        employee.setDelete_flag(0);
        // 3. 登録日時＝更新日時
        employee.setCreated_at(LocalDateTime.now());
        employee.setUpdated_at(LocalDateTime.now());
        // 4. DBへ
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
    public String postEmployee(@PathVariable("id") Integer id, @Validated Employee employee, BindingResult res, Model model, @RequestParam String newpassword ) {
        if (res.hasErrors()) {
            return getEdit(id, employee, model);
        }
        // DBから取得したemployeeに対してformでの変更を加え、DBに書込
        Employee emplDb = service.getEmployee(id);
        // 1. name の更新・・・employee
        emplDb.setName(employee.getName());
        // 2. 更新日時は更新
        emplDb.setUpdated_at(LocalDateTime.now());

        Authentication authDb = emplDb.getAuthentication();
        Authentication authForm = employee.getAuthentication();

        // 3. passwordの更新（変更があった時だけ）・・・authentication
        //  ・リクエストパラメータのnewpasswordをemployee.authentication.passwordにセットする
        authForm.setPassword(newpassword);
        if (!authForm.getPassword().equals("")) {
            // ・passwordが空白ではなかった=パスワードの編集があった時は、passwordをセットする
            authDb.setPassword(authForm.getPassword());
        }
        // 4. roleの更新・・・authentication
        authDb.setRole(authForm.getRole());
        // 5. DBへ登録する
        service.saveEmployee(emplDb);
        return "redirect:/employee/list";
    }

    /** 削除処理 */
    @GetMapping("/delete/{id}")
    public String postDelete(@PathVariable("id") Integer id) {
        Employee emplDb = service.getEmployee(id);
        emplDb.setDelete_flag(1);
        service.saveEmployee(emplDb);
        return "redirect:/employee/list";
    }

}
