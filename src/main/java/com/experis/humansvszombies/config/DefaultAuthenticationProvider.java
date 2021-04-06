package com.experis.humansvszombies.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.ArrayList;
/*
* Implements the AuthenticationProvider interface. Giving access to JWT's subject_id,
* roles, authentication object and a boolean check returning does the token belong to an admin.
*
 */
public class DefaultAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public String getPrincipal() {
        return this.getAuthentication().getPrincipal().toString();
    }

    @Override
    public ArrayList<GrantedAuthority> getAuthorities() {
        return new ArrayList<>(this.getAuthentication().getAuthorities());
    }

    @Override
    public Boolean isAdmin() {
        ArrayList<GrantedAuthority> authorities = this.getAuthorities();
        SimpleGrantedAuthority admin = new SimpleGrantedAuthority("ROLE_admin");
        return authorities.contains(admin);
    }
}
