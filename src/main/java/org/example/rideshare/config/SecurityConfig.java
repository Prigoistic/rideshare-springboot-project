package org.example.rideshare.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Disables Spring Security's default login page and permits all requests.
 *
 * This project doesn't define authentication yet, but includes spring-security
 * as a dependency (for future JWT support). Without this explicit config,
 * Spring Security auto-configures a default form login at /login.
 */
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // We are not using browser forms or sessions; disable CSRF for now
            .csrf(csrf -> csrf.disable())
            // Stateless because we plan to use token-based auth in the future
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // Do not enable the default login page or HTTP Basic dialog
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable())
            // Allow all endpoints (adjust later when adding real auth)
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
            // No additional customization needed
            .logout(Customizer.withDefaults());

        return http.build();
    }
}
