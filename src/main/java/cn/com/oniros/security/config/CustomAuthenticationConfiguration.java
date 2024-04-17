package cn.com.oniros.security.config;

import cn.com.oniros.security.filter.JwtFilter;
import cn.com.oniros.security.handler.AuthenticationExceptionHandler;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

/**
 * cn.com.oniros.security.config.AuthenticationConfiguration
 *
 * @author Li Xiaoxu
 * 2024/4/14 23:02
 */
@Configuration
@EnableWebSecurity
public class CustomAuthenticationConfiguration {

    @Resource
    private AuthenticationExceptionHandler exceptionHandler;

    @Resource
    private JwtFilter jwtFilter;

    @Resource
    private PermitPathConfig permitPathConfig;

    @Bean
    @Order(1)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        List<String> permittedPath = permitPathConfig.getPermittedPath();
        http
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(registry -> {
                    registry.requestMatchers("/asp/auth/v1/login").permitAll();
                    permittedPath.forEach(path -> registry.requestMatchers(path).permitAll());
                })
                .authorizeHttpRequests(registry ->
                        registry.anyRequest().authenticated())
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(configure -> configure.authenticationEntryPoint(exceptionHandler));
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
