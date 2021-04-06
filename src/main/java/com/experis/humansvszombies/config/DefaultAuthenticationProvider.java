package com.experis.humansvszombies.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
/*
* Offers access to Authentication context, tokens subject_id
* and list of roles.
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
}
