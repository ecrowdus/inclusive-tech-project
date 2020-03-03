package com.prefchefs.findfood.service;

import com.prefchefs.findfood.dao.PrefChefsUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("userDetailsService")
public class PrefChefsUserDetailsService implements UserDetailsService {

    private String usersByUsernameQuery = "select * from users where username = ?";
    private String authoritiesByUsernameQuery = "select username,authority from authorities where username = ?";

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
            return new PrefChefsUser(username1, password, enabled, true, true, true, AuthorityUtils.NO_AUTHORITIES, first_name, last_name);
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
        return new PrefChefsUser(returnUsername, userFromUserQuery.getPassword(), userFromUserQuery.isEnabled(), userFromUserQuery.isAccountNonExpired(), userFromUserQuery.isCredentialsNonExpired(), userFromUserQuery.isAccountNonLocked(), combinedAuthorities, userFromUserQuery.getFirst_name(), userFromUserQuery.getLast_name());
    }

    private JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }


}
