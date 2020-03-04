package com.prefchefs.findfood.controller;

import com.prefchefs.findfood.dao.PrefChefsUser;
import com.prefchefs.findfood.service.PrefChefsUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    PrefChefsUserDetailsService userDetailsService;

    @GetMapping("/login")
    public String login(Model model) {
//        BCryptPasswordEncoder b = new BCryptPasswordEncoder();
//        System.out.println(b.encode("qwerty"));
        return "login";
    }

    @PostMapping("/signup")
    public String signup(@RequestParam("username") String username, @RequestParam("first_name") String firstName,
                         @RequestParam("last_name") String lastName, @RequestParam("password") String password) {

        userDetailsService.addUser(username, password, firstName, lastName);

        PrefChefsUser user = userDetailsService.loadUserByUsername(username);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
//        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);



        return "redirect:/profile/edit?initial=true";
    }

    private void auth(String username) {

    }

}
