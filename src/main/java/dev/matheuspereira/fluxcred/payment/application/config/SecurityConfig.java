package dev.matheuspereira.fluxcred.payment.application.config;

import dev.matheuspereira.fluxcred.payment.infrastructure.security.JwtAuthTokenFilter;
import dev.matheuspereira.fluxcred.payment.infrastructure.security.JwtUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  private final JwtAuthTokenFilter jwtAuthTokenFilter;
  private final JwtUserService jwtUserService;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    log.info("Initialized bean securityFilterChain");
    http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(authorizeHttpRequests -> {
          authorizeHttpRequests
              .requestMatchers("/auth/**", "/swagger-ui/**", "/api-docs/**").permitAll()
              .anyRequest().authenticated();
        })
        .formLogin(AbstractHttpConfigurer::disable)
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(jwtAuthTokenFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(jwtUserService);
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return authenticationProvider;

  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }
}
