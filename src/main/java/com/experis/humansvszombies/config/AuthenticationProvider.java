package com.experis.humansvszombies.config;

import org.springframework.context.annotation.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public interface AuthenticationProvider {
    Authentication getAuthentication();
    String getPrincipal();
    List<GrantedAuthority> getAuthorities();
    Boolean isAdmin();
}
