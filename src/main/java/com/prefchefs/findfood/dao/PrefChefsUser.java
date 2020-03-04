package com.prefchefs.findfood.dao;

import com.prefchefs.findfood.RestrictionType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class PrefChefsUser extends User {

    private String first_name;
    private String last_name;
    private List<Restriction> restrictions;

    public PrefChefsUser(String username, String password, boolean enabled, boolean accountNonExpired,
                       boolean credentialsNonExpired, boolean accountNonLocked,
                       Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public PrefChefsUser(String username, String password, boolean enabled, boolean accountNonExpired,
                         boolean credentialsNonExpired, boolean accountNonLocked,
                         Collection<? extends GrantedAuthority> authorities, String first_name, String last_name, List<Restriction> restrictions) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.first_name = first_name;
        this.last_name = last_name;
        this.restrictions = restrictions;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public List<Restriction> getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(List<Restriction> restrictions) {
        this.restrictions = restrictions;
    }

    public List<Restriction> getDietaryRestrictions() {
        return restrictions.stream().filter(r -> r.getType() == RestrictionType.RESTRICTION).collect(Collectors.toList());
    }

    public List<Restriction> getAllergies() {
        return restrictions.stream().filter(r -> r.getType() == RestrictionType.ALLERGEN).collect(Collectors.toList());
    }

}
