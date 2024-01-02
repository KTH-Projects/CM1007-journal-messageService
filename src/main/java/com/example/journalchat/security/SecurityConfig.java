package com.example.journalchat.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.cors(AbstractHttpConfigurer::disable);

        http.cors(core->{
            core.configurationSource(corsConfigurationSource());
        });

        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(authorize -> {
            authorize.requestMatchers(HttpMethod.POST,"/message/send").permitAll()
                    .requestMatchers(HttpMethod.GET,"/message").permitAll()
                    .requestMatchers(HttpMethod.GET,"/message/chat").permitAll()
                    .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                    .anyRequest().authenticated();
        });


        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));

        http.sessionManagement(
                t->{
                    t.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                }
        );
        return http.build();
    }

    @Bean
    public DefaultMethodSecurityExpressionHandler msecurity(){
        DefaultMethodSecurityExpressionHandler defaultMethodSecurityExpressionHandler = new DefaultMethodSecurityExpressionHandler();
        defaultMethodSecurityExpressionHandler.setDefaultRolePrefix("");
        return defaultMethodSecurityExpressionHandler;
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            // Get realm_access from the JWT where the roles are
            Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");
            if (realmAccess != null && realmAccess.containsKey("roles")) {
                ((List<String>) realmAccess.get("roles")).forEach(

                        role -> {
                            System.out.println(role);
                            grantedAuthorities.add(new SimpleGrantedAuthority(role));}
                );
            }
            return grantedAuthorities;
        });
        return jwtAuthenticationConverter;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                System.getenv("ACCOUNT_SERVICE_URL"),
                System.getenv("FRONTEND_URL")
        )); // Adjust as necessary
        //configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // Adjust as necessary
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type","Access-Control-Allow-Origin","userCookieID"));
        configuration.addExposedHeader("Access-Control-Allow-Credentials");
        configuration.addExposedHeader("Access-Control-Allow-Origin");
        configuration.addExposedHeader("Authorization");
        configuration.addExposedHeader("userCookieID");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source; // This is actually an instance of UrlBasedCorsConfigurationSource
    }


}
