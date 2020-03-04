package com.prefchefs.findfood.controller;

import com.prefchefs.findfood.dao.PrefChefsUser;
import com.prefchefs.findfood.dao.Restriction;
import com.prefchefs.findfood.service.PrefChefsUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    PrefChefsUserDetailsService userService;

    @GetMapping("")
    public String home(Model model) {
        PrefChefsUser user = (PrefChefsUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("restrictions", user.getDietaryRestrictions());
        model.addAttribute("allergies", user.getAllergies());
        return "profile";
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam(value = "initial", required = false) boolean initial) {
        PrefChefsUser user = (PrefChefsUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("restrictions", user.getDietaryRestrictions());
        model.addAttribute("allergies", user.getAllergies());
        return "profileEdit";
    }

    @PostMapping("/edit")
    public ModelAndView updateRestrictions(@RequestParam(value = "restrictions", required = false, defaultValue = "") String[] restrictions,
                                           @RequestParam(value = "allergies", required = false, defaultValue = "") String[] allergies, ModelMap model,
                                           @RequestParam(value = "initial", required = false) boolean initial) {

        PrefChefsUser user = (PrefChefsUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Restriction> combined =  userService.udpateRestrictions(user.getUsername(), restrictions, allergies);
        user.setRestrictions(combined);

        // Update the user object after changing restrictions
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        model.addAttribute("updated", true);
        return new ModelAndView(initial ? "redirect:/home" : "redirect:/profile", model);
    }

}
