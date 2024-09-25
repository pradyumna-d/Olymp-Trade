package killhouser.trading.config.authentication;

import killhouser.trading.common.authentication.AuthEntryPointJwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

  //  private final JwtRequestFilter jwtRequestFilter;
  private final AuthEntryPointJwt authEntryPointJwt;
  private final AuthenticationConfig authenticationConfig;

  @Autowired
  public WebSecurityConfig(
      //      @Qualifier("JwtRequestFilter") JwtRequestFilter jwtRequestFilter,
      AuthEntryPointJwt authEntryPointJwt, AuthenticationConfig authenticationConfig) {
    //    this.jwtRequestFilter = jwtRequestFilter;
    this.authEntryPointJwt = authEntryPointJwt;
    this.authenticationConfig = authenticationConfig;
  }

  @Bean
  public SecurityFilterChain configure(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .exceptionHandling(exception -> exception.authenticationEntryPoint(authEntryPointJwt))
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .cors()
        .and()
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers(authenticationConfig.getWhitelistedApis())
                    .permitAll()
                    .anyRequest()
                    .authenticated());

    //    http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(authenticationConfig.getCors().getAllowedOrigins());
    configuration.setAllowedMethods(authenticationConfig.getCors().getAllowedMethods());
    configuration.setAllowedHeaders(authenticationConfig.getCors().getAllowedHeaders());
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
