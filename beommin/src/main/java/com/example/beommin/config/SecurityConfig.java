package com.example.beommin.config;

import com.example.beommin.security.jwt.CustomAccessDeniedHandler;
import com.example.beommin.security.jwt.CustomAuthenticationEntryPoint;
import com.example.beommin.security.oauth.CustomAuthenticationFailureHandler;
import com.example.beommin.security.oauth.CustomAuthenticationSuccessHandler;
import com.example.beommin.security.jwt.JwtAuthenticationFilter;
import com.example.beommin.security.oauth.service.CustomOAuth2UserService;
import com.example.beommin.security.oauth.CookieAuthorizationRequestRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

  private final CustomOAuth2UserService customOAuth2UserService;
  private final CookieAuthorizationRequestRepository cookieAuthorizationRequestRepository;
  private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
  private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final CustomAuthenticationEntryPoint authenticationEntryPoint;
  private final CustomAccessDeniedHandler accessDeniedHandler;

//  @Override
//  public void configure(WebSecurity web) throws Exception {
//    web.ignoring().antMatchers("/h2-console/**", "/favicon.cio");
//  }
  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.authorizeRequests()
        .antMatchers("/oauth2/**", "/auth/**").permitAll()
        .antMatchers("/admin/**").hasRole("ADMIN")
//        .antMatchers(HttpMethod.GET, "/api/v1/users").hasRole("USER")
//        .antMatchers(HttpMethod.POST, "/api/v1/users/").hasRole("USER")
        .anyRequest().permitAll();

    http.cors()                     // CORS on
        .and()
        .csrf().disable()           // CSRF off
        .httpBasic().disable()      // Basic Auth off
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);    // Session off


    http.formLogin().disable()
        .oauth2Login()
          .authorizationEndpoint()
            .baseUri("/oauth2/authorize")
            .authorizationRequestRepository(cookieAuthorizationRequestRepository)
          .and()
          .redirectionEndpoint()
//            .baseUri("/oauth2/callback/*")
          .and()
          .userInfoEndpoint()
            .userService(customOAuth2UserService)
          .and()
          .successHandler(customAuthenticationSuccessHandler)
          .failureHandler(customAuthenticationFailureHandler);

    http.exceptionHandling()
        .authenticationEntryPoint(authenticationEntryPoint)
        .accessDeniedHandler(accessDeniedHandler);

//    http.addFilterAfter(jwtAuthenticationFilter, LogoutFilter.class);
    http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//    http.addFilterAfter(jwtAuthenticationFilter, SecurityContextPersistenceFilter.class);

//    http.logout()
//        .deleteCookies("JSESSIONID");
  }

}
