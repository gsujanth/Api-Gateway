package com.example.gateway.ZuulGateway.security.config;

import com.example.gateway.ZuulGateway.security.CustomAuthenticationEntryPoint;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class GatewayConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Disable CSRF (cross site request forgery)
        http.cors().and().csrf().disable()
        // No session will be created or used by spring security
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
            // Entry points
            .authorizeRequests()
                .antMatchers("/practice/**").permitAll()
                .antMatchers("/actuator/**").permitAll()
                // Disallow everything else..
                .anyRequest().authenticated()
                //.and()
                //.addFilterBefore(getAuthenticationFilter(),UsernamePasswordAuthenticationFilter.class);

        // If a user try to access a resource without having enough permissions
        .and()
                //.exceptionHandling().accessDeniedPage("/login")
            .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());
    }
}
