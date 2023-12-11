package com.ufncc.workorder.Configuration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@AllArgsConstructor
public class WebSecurityConfig {

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity https) throws Exception{
        https
                .authorizeHttpRequests((authorize) -> authorize
                        // All users can register and save a new account;
                        .requestMatchers("/auth/register", "/auth/save", "/").permitAll()

                        // Only 'ADMINS' and 'TECNICO' can access the User's CRUD
                        .requestMatchers("/user/list").hasAnyRole("ADMIN","TECNICO")
                        .requestMatchers("/user/updade/*").hasAnyRole("ADMIN","TECNICO")
                        .requestMatchers("/user/delete/*").hasAnyRole("ADMIN","TECNICO")

                        // Only 'ADMINS' and 'TECNICO' and 'SOLICITANTE' can access the Order's CRUD
                        .requestMatchers("/order/list").hasAnyRole("ADMIN","TECNICO", "SOLICITANTE")
                        .requestMatchers("/order/updade/*").hasAnyRole("ADMIN","TECNICO", "SOLICITANTE")
                        .requestMatchers("/order/delete/*").hasAnyRole("ADMIN","TECNICO", "SOLICITANTE")

                        // DEBUG: Remove after
                        //TODO: remove prototype page for order's crud.
                        .requestMatchers("/user/solicita").hasRole("SOLICITANTE")
                        .requestMatchers("/user/arruma").hasRole("TECNICO")

                                // All other requests, authentication is required.
                        .anyRequest().authenticated()

                // All users can log in into the system.
                ).formLogin(
                        form -> form
                                .loginPage("/auth/login")
                                .loginProcessingUrl("/auth/login")
                                .defaultSuccessUrl("/auth/welcome")
                                .permitAll()

                // All users can log out of the system.
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"))
                                .permitAll()
                );
        return https.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
