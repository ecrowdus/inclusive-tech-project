package com.prefchefs.findfood.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prefchefs.findfood.DiningHall;
import com.prefchefs.findfood.dao.MenuData;
import com.prefchefs.findfood.exception.MenuNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class MenuService {

    private String menusByDateAndLocationQuery = "select * from menus where dining_hall = ? and date = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public MenuData loadMenuByDateAndLocation(DiningHall diningHall, Date date) throws MenuNotFoundException {
        List<MenuData> menus = loadMenusByDateAndLocation(diningHall, date);
        if(menus.size() == 0) {
            throw new MenuNotFoundException(diningHall, date);
        }
        else {
            return menus.get(0);
        }
    }

    protected List<MenuData> loadMenusByDateAndLocation(DiningHall diningHall, Date date) {
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd")
//        String dateString = df.format(date);
        return this.getJdbcTemplate().query(this.menusByDateAndLocationQuery, new Object[]{diningHall.name(), date}, (rs, rowNum) -> {
            String o = rs.getString("data");
            MenuData menuData = null;
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                menuData = objectMapper.readValue(o, MenuData.class);
            } catch(IOException e) {
                e.printStackTrace();
            }
            return menuData;
        });
    }

    private JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

}
