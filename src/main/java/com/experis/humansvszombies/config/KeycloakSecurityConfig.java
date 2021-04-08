package com.experis.humansvszombies.config;


import com.experis.humansvszombies.controllers.exceptions.APIAccessDeniedHandler;
import com.experis.humansvszombies.controllers.exceptions.APINotAuthorizedHandler;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true)
public class KeycloakSecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.cors().and().csrf().disable()
                //APINotAuthorizedHandler provides custom error response when request isn't authorized
                .exceptionHandling().authenticationEntryPoint(new APINotAuthorizedHandler()).and()
                //APIAccessDeniedHandler provides custom error response when requester doesn't have privileges
                .exceptionHandling().accessDeniedHandler(new APIAccessDeniedHandler()).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests().mvcMatchers(HttpMethod.DELETE, "/api/v1/games/{\\d+}").hasRole("admin")
                .mvcMatchers(HttpMethod.POST, "/api/v1/games").hasRole("admin")
                .mvcMatchers(HttpMethod.PUT, "/api/v1/games/{\\d+}").hasRole("admin")
                .mvcMatchers(HttpMethod.PUT, "/api/v1/games/{\\d+}/players/{\\d+}").hasRole("admin")
                .mvcMatchers(HttpMethod.DELETE, "/api/v1/games/{\\d+}/players/{\\d+}").hasRole("admin")
                .mvcMatchers(HttpMethod.DELETE, "/api/v1/games/{\\d+}/kill/{\\d+}").hasRole("admin")
                .mvcMatchers(HttpMethod.PUT, "/api/v1/games/{\\d+}/kill/{\\d+}").hasRole("admin")
                .anyRequest()
                .permitAll();
    }

    //SimpleAuthorityMapper makes sure roles are not prefixed with 'ROLE_'
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        KeycloakAuthenticationProvider keycloakAuthenticationProvider= keycloakAuthenticationProvider();
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
        auth.authenticationProvider(keycloakAuthenticationProvider);
    }

    @Bean
    public KeycloakSpringBootConfigResolver KeycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }

    //use NullAuthenticatedSessionStrategy to avoid cookie based sessions
    @Bean
    @Override
    protected NullAuthenticatedSessionStrategy sessionAuthenticationStrategy() {
        return new NullAuthenticatedSessionStrategy();
    }
    //a bean providing authentication object. Gives access to information about JWT token.
    @Bean
    public DefaultAuthenticationProvider defaultAuthenticationProvider(){
        return new DefaultAuthenticationProvider();
    }


}