package com.prefchefs.findfood.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.prefchefs.findfood.RestrictionType;
import com.prefchefs.findfood.dao.PrefChefsUser;
import com.prefchefs.findfood.dao.Restriction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("userDetailsService")
public class PrefChefsUserDetailsService implements UserDetailsService {

    private String usersByUsernameQuery = "select * from users where username = ?";
    private String authoritiesByUsernameQuery = "select username,authority from authorities where username = ?";
    private String updateRestrictionsQuery = "update users set restrictions = ? where username = ?";
    private String addUserQuery = "insert into users(username, password, first_name, last_name) values (?, ?, ?, ?)";
    private String addUserAuthorityQuery = "insert into authorities(username, authority) values (?, 'ROLE_USER')";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public PrefChefsUser loadUserByUsername(String username) throws UsernameNotFoundException {
        List<PrefChefsUser> users = loadUsersByUsername(username);
        if (users.size() == 0) {
//            this.logger.debug("Query returned no results for user '" + username + "'");
            throw new UsernameNotFoundException("Username " + username + "not found");
        }
        else {
            PrefChefsUser user = users.get(0);
            Set<GrantedAuthority> dbAuthsSet = new HashSet<>();
            dbAuthsSet.addAll(this.loadUserAuthorities(user.getUsername()));

//            if(this.enableGroups) {
//                dbAuthsSet.addAll(this.loadGroupAuthorities(user.getUsername()));
//            }

            List<GrantedAuthority> dbAuths = new ArrayList<>(dbAuthsSet);
//            this.addCustomAuthorities(user.getUsername(), dbAuths);

            if (dbAuths.size() == 0) {
//                this.logger.debug("User '" + username + "' has no authorities and will be treated as 'not found'");
                throw new UsernameNotFoundException("Username " + username + "not found");
            }
            else {
                return this.createUserDetails(username, user, dbAuths);
            }
        }
    }

    protected List<PrefChefsUser> loadUsersByUsername(String username) {
        return this.getJdbcTemplate().query(this.usersByUsernameQuery, new String[]{username}, (rs, rowNum) -> {
            String username1 = rs.getString("username");
            String password = rs.getString("password");
            boolean enabled = rs.getBoolean("enabled");
            String first_name = rs.getString("first_name");
            String last_name = rs.getString("last_name");

            String restrictionsString = rs.getString("restrictions");
            ObjectMapper objectMapper = new ObjectMapper();
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            List<Restriction> restrictions = null;
            try {
                restrictions = objectMapper.readValue(restrictionsString, typeFactory.constructCollectionType(List.class, Restriction.class));
            }
            catch(IOException e) {
                e.printStackTrace();
            }
            return new PrefChefsUser(username1, password, enabled, true, true, true, AuthorityUtils.NO_AUTHORITIES, first_name, last_name, restrictions);
        });
    }

    protected List<GrantedAuthority> loadUserAuthorities(String username) {
        return this.getJdbcTemplate().query(this.authoritiesByUsernameQuery, new String[]{username}, (rs, rowNum) -> {
            String roleName = rs.getString("authority");
            return new SimpleGrantedAuthority(roleName);
        });
    }

    protected PrefChefsUser createUserDetails(String username, PrefChefsUser userFromUserQuery, List<GrantedAuthority> combinedAuthorities) {
        String returnUsername = userFromUserQuery.getUsername();
        return new PrefChefsUser(returnUsername, userFromUserQuery.getPassword(), userFromUserQuery.isEnabled(), userFromUserQuery.isAccountNonExpired(), userFromUserQuery.isCredentialsNonExpired(), userFromUserQuery.isAccountNonLocked(), combinedAuthorities, userFromUserQuery.getFirst_name(), userFromUserQuery.getLast_name(), userFromUserQuery.getRestrictions());
    }

    public List<Restriction> udpateRestrictions(String username, String[] restrictions, String[] allergies) {
        List<Restriction> combined = new ArrayList<>();
        for(String r : restrictions) {
            combined.add(new Restriction(r.toLowerCase(), RestrictionType.RESTRICTION));
        }
        for(String a : allergies) {
            combined.add(new Restriction(a.toLowerCase(), RestrictionType.ALLERGEN));
        }

        updateRestrictions(username, combined);
        return combined;
    }

    public void updateRestrictions(String username, List<Restriction> restrictions) {
        ObjectMapper mapper = new ObjectMapper();
        String json = "[]";
        try {
            json = mapper.writeValueAsString(restrictions);
        } catch(JsonProcessingException e) {
            e.printStackTrace();
        }
        this.getJdbcTemplate().update(updateRestrictionsQuery, new String[]{json, username}, new int[]{Types.OTHER, Types.VARCHAR});
    }

    public void addUser(String username, String password, String firstName, String lastName) {
        password = new BCryptPasswordEncoder().encode(password);
        this.getJdbcTemplate().update(addUserQuery, new String[]{username, password, firstName, lastName}, new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR});
        this.getJdbcTemplate().update(addUserAuthorityQuery, new String[]{username}, new int[]{Types.VARCHAR});
    }

    private JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }


}
