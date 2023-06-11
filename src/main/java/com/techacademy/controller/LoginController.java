package com.techacademy.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    /** ログイン時にログイン状態をテンプレートに渡したい */
    @GetMapping("/login")
    public String getLogin(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("isLoggedIn", true);
            model.addAttribute("username", principal.getName());
        } else {
            model.addAttribute("isLoggedIn", false);
        }
        return "login";
    }

}
