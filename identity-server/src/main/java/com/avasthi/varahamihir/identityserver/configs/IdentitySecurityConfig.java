package com.avasthi.varahamihir.identityserver.configs;

import com.avasthi.varahamihir.identityserver.filters.JwtAuthenticationFilter;
import com.avasthi.varahamihir.identityserver.handlers.IdentityAccessDeniedHandler;
import com.avasthi.varahamihir.identityserver.handlers.IdentityAuthenticationFailureHandler;
import com.avasthi.varahamihir.identityserver.utils.Paths;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class IdentitySecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(HttpMethod.GET, "/content/**", Paths.V1.Content.fullPath + "/**", Paths.V1.Equation.fullPath+ "/**").permitAll()
                        .requestMatchers(HttpMethod.POST, Paths.V1.Users.fullPath, Paths.V1.Auth.fullPath).permitAll()
                        .anyRequest()
                        .authenticated())
                .sessionManagement((sm) -> {
                    sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                    sm.sessionAuthenticationFailureHandler(new IdentityAuthenticationFailureHandler());
                })
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(lo -> {
                    lo.logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());
                    lo.addLogoutHandler(logoutHandler);
                })
        ;
        httpSecurity.exceptionHandling(customizer -> {
            customizer.accessDeniedHandler(new IdentityAccessDeniedHandler());
        });
        return httpSecurity.build();
    }
}
