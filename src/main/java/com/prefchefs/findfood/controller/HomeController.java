package com.prefchefs.findfood.controller;

import com.prefchefs.findfood.DiningHall;
import com.prefchefs.findfood.Meal;
import com.prefchefs.findfood.dao.MenuData;
import com.prefchefs.findfood.dao.PrefChefsUser;
import com.prefchefs.findfood.dao.Station;
import com.prefchefs.findfood.exception.MenuNotFoundException;
import com.prefchefs.findfood.service.MenuService;
import com.prefchefs.findfood.service.RestrictionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Controller()
@RequestMapping("/home")
public class HomeController {

    @Autowired
    MenuService menuService;

    @Autowired
    RestrictionsService restrictionsService;

    @GetMapping("")
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return "home";
    }

    @GetMapping("meal")
    public String meal(Model model, @RequestParam("meal") Meal meal, @RequestParam("diningHall") DiningHall diningHall, @RequestParam("date") Date date) {
        PrefChefsUser user = (PrefChefsUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        model.addAttribute("meal", meal.toString());
        model.addAttribute("diningHall", diningHall.toString());
        DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
        model.addAttribute("date", df.format(date));
        model.addAttribute("menu", new ArrayList<Station>()); // empty list in case no menu is found
        try {
            MenuData md = menuService.loadMenuByDateAndLocation(diningHall, date);
            model.addAttribute("menu", md.getMealWithStatus(meal, user.getRestrictions(), restrictionsService));
        }
        catch(MenuNotFoundException e) {
            //return "Menu not found!";
        }
        return "panes/hallPane";
    }

}
