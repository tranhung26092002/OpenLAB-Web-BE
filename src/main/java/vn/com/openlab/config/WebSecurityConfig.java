package vn.com.openlab.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import vn.com.openlab.filter.JwtTokenFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtTokenFilter jwtTokenFilter;
    private final AuthenticationProvider authenticationProvider;

    @Value("${api.prefix}")
    private String apiPrefix;

    @Value("${domain.protocol}")
    private String domainProtocol;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Enable CORS
            .addFilterAfter(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(request -> request
                    .requestMatchers(HttpMethod.GET,
                            String.format("%s/products/**", apiPrefix),
                            String.format("%s/products/images/*", apiPrefix),
                            String.format("%s/orders/**", apiPrefix),
                            String.format("%s/orders_details/**", apiPrefix),
                            String.format("%s/roles/**", apiPrefix),
                            String.format("%s/health_check/**", apiPrefix),
                            String.format("%s/actuator/**", apiPrefix),
                            String.format("%s/comments/**", apiPrefix),

                            "/login/**",
                            "/oauth2/**",
                            "/v2/api-docs",
                            "/v3/api-docs",
                            "/v3/api-docs/**",
                            "/swagger-resources/**",
                            "/swagger-ui.html",
                            "/webjars/**",
                            "/swagger-resources/configuration/ui",
                            "/swagger-resources/configuration/security",
                            "/swagger-ui.html/**",
                            "/swagger-ui/**",
                            "/swagger-ui.html/**"
                    ).permitAll()
                    .requestMatchers(HttpMethod.POST,
                            String.format("%s/users/register", apiPrefix),
                            String.format("%s/users/login", apiPrefix),
                            String.format("%s/users/refresh-token", apiPrefix)
                    ).permitAll()
                    .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider)
            .exceptionHandling(exception -> exception
                    .authenticationEntryPoint((request, response, authException) ->
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage()))
                    .accessDeniedHandler((request, response, accessDeniedException) ->
                            response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage()))
            )
            .headers(headers -> headers
                    .httpStrictTransportSecurity(hsts -> hsts
                            .includeSubDomains(true)
                            .maxAgeInSeconds(31536000)
                    )
                    .contentSecurityPolicy(csp -> csp
                            .policyDirectives("default-src 'self'; img-src 'self' data:; script-src 'self'")
                    )
                    .permissionsPolicy(fp -> fp
                            .policy("geolocation 'self'; microphone 'none'; camera 'none'")
                    )
            );

        http.securityMatcher(String.valueOf(EndpointRequest.toAnyEndpoint()));
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(domainProtocol, "http://14.225.255.177:8081", "http://localhost:3000", "http://localhost:8081")); // Define allowed origins
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        configuration.setAllowCredentials(true); // Nếu có cần truyền Cookie
        configuration.setExposedHeaders(List.of("Authorization")); // Nếu cần trả về các header cụ thể

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
