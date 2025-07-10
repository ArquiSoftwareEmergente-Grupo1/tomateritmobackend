package com.tomateritmo.arqemergente.iam.infrastructure.authorization.sfs.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

/**
 * Web Security Configuration.
 * <p>
 * This class is responsible for configuring the web security.
 * It removes all authentication requirements and allows unrestricted access to all endpoints.
 * </p>
 */
@Configuration
public class WebSecurityConfiguration {

  /**
   * This method creates the security filter chain.
   * It configures the http security to allow unrestricted access.
   *
   * @param http The http security
   * @return The security filter chain
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.cors(corsConfigurer -> corsConfigurer.configurationSource(request -> {
      var cors = new CorsConfiguration();
      // Usar las mismas configuraciones que en WebConfig
      cors.setAllowedOrigins(List.of(
          "http://localhost:4200",  // Desarrollo local
          "https://tomateritmo-frontend.web.app"  // Frontend en Firebase
      ));
      cors.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
      cors.setAllowedHeaders(List.of("*"));
      cors.setExposedHeaders(List.of("Authorization"));
      cors.setAllowCredentials(true);
      return cors;
    }));
    http.csrf(csrfConfigurer -> csrfConfigurer.disable())
            .authorizeHttpRequests(authorizeRequests -> authorizeRequests.anyRequest().permitAll());
    return http.build();
  }
}