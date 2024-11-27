package br.com.fsrocha.vrcontadigital.infrastructure.security;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SecurityConfig {
    private static final String DEFAULT_ROLE = "USER";

    @Value("${demo.username:username}")
    String username;

    @Value("${demo.password:password}")
    String password;

    @Bean
    public UserDetailsService userDetailsService() {
        var user = User.builder()
                .username(username)
                .password(passwordEncoder().encode(password))
                .roles(DEFAULT_ROLE)
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        var authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
        var authenticationManager = authenticationManagerBuilder.build();

        http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .authenticationManager(authenticationManager)
                .httpBasic(Customizer.withDefaults());

        http.csrf(csrf ->
                csrf.ignoringRequestMatchers("/contascorrente/**")
                        .csrfTokenRepository(new CookieCsrfTokenRepository())
        ).sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.NEVER));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
