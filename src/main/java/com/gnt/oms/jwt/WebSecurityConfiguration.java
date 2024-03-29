package com.gnt.oms.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfiguration {

  @Autowired
  private JwtFilter authFilter;

  /*
   * @Bean
   * public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
   * Exception {
   * return
   * http.csrf().disable().authorizeHttpRequests().requestMatchers("authenticate",
   * "/sign-up").permitAll().
   * and().authorizeHttpRequests().requestMatchers("/api/**").
   * authenticated().and().sessionManagement().sessionCreationPolicy(
   * SessionCreationPolicy.STATELESS).
   * and().authFilterBefore(authFilter,
   * UsernamePasswordAuthenticationFilter.class).build();
   * }
   */

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable);
    http.authorizeHttpRequests(rQ -> {
      rQ.requestMatchers("/authenticate", "/sign-up", "/apis/v1/*", "/apis/v1/*/*",
          "/user/login", "/user/signup", "/user/forgotPassword", "/yfsuser/*", "/category/*", "/product/*", "/product/delete/*",
          "/product/getByCategory/*", "/product/getByProductId/*", 
          "/bill/*", "/bill/delete/*", "/dashboard/*", "/yfsuser/checkToken/*").permitAll();        
      rQ.requestMatchers("/api/**").authenticated();
    });

    http.sessionManagement(sessionAuthenticationStrategy -> sessionAuthenticationStrategy
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    // http.authenticationProvider(authenticationProvider());
    http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

}
