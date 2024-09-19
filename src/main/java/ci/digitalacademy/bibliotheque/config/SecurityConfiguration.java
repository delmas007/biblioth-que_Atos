package ci.digitalacademy.bibliotheque.config;

import ci.digitalacademy.bibliotheque.security.AuthorityConstants;
import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.http.HttpMethod.*;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(CsrfConfigurer::disable) // Désactiver la protection CSRF pour cette configuration
                .authorizeHttpRequests((authorize) -> authorize
                                .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
                                .requestMatchers("/api/authenticate").permitAll()
                                .requestMatchers(POST,"/api/users").permitAll()
                                .requestMatchers(POST,"/api/loans/reservation").hasAuthority(AuthorityConstants.USER)
                                .requestMatchers(GET,"/api/books/**").hasAuthority(AuthorityConstants.USER)
                                .requestMatchers(GET,"/api/users/slug/**").hasAuthority(AuthorityConstants.USER)
                                .requestMatchers(PUT,"/api/users,/cancel/**").hasAuthority(AuthorityConstants.USER)
                                .requestMatchers("/api/users/**,/api/loans/**").hasAuthority(AuthorityConstants.ADMIN)
                                .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
