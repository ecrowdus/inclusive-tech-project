package com.prefchefs.findfood.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.prefchefs.findfood.dao.MenuData;
import com.prefchefs.findfood.exception.RestrictionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RestrictionsService {

    private String ingredientsByRestrictionQuery = "select ingredients from restrictions where name = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<String> getIngredients(String name) throws RestrictionNotFoundException {
        List<List<String>> ingredientsLists = getIngredientsLists(name);
        if(ingredientsLists.size() == 0) {
            throw new RestrictionNotFoundException(name);
        }
        else return ingredientsLists.get(0);
    }

    public List<List<String>> getIngredientsLists(String name) {
        return jdbcTemplate.query(ingredientsByRestrictionQuery, new Object[]{ name }, (rs, rowNum) -> {
            String json = rs.getString("ingredients");
            ObjectMapper objectMapper = new ObjectMapper();
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            List<String> ingredients = new ArrayList<>();

            try {
                ingredients = objectMapper.readValue(json, typeFactory.constructCollectionType(List.class, String.class));
            } catch(IOException e) {
                e.printStackTrace();
            }

            return ingredients;
        });
    }
}
