package com.prefchefs.findfood.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(Model model) {
//        BCryptPasswordEncoder b = new BCryptPasswordEncoder();
//        System.out.println(b.encode("qwerty"));
        return "login";
    }

}
