package com.javarush.lesson18.config;


import com.javarush.lesson18.entity.Role;
import com.javarush.lesson18.service.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final AuthService authService;

    public SecurityConfiguration(AuthService authService) {
        this.authService = authService;
    }


    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(r -> r

                        .requestMatchers("/registration", "/login", "/logout")
                        .permitAll()

                        .requestMatchers("/users")
                        .hasAnyRole(
                                Role.USER.getAuthority(),
                                Role.ADMIN.getAuthority())

                        .requestMatchers(HttpMethod.POST, "/users")
                        .hasAnyRole(
                                Role.USER.getAuthority(),
                                Role.ADMIN.getAuthority()
                        )

                        .anyRequest().authenticated()
                )
                .formLogin(formConfig -> formConfig
                        .loginPage("/login")
                        .usernameParameter("login")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/users", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .oauth2Login(o2l -> o2l
                        .loginPage("/login")
                        .authorizationEndpoint(ae->ae.baseUri("/oauth"))
                        .successHandler(getSuccessHandler())
                )

                .logout(logoutConfig -> logoutConfig
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                        .logoutSuccessUrl("/login?logout=true"))
                .build();
    }

    private AuthenticationSuccessHandler getSuccessHandler() {
        return (request, response, authentication) -> {
            Object principal = authentication.getPrincipal();
            DefaultOAuth2User oAuth2User = (DefaultOAuth2User) principal;
            String login = oAuth2User.getAttributes()
                    .entrySet()
                    .stream()
                    .filter(entry ->
                            entry.getKey().equals("login")
                            || entry.getKey().equals("email"))
                    .map(e -> e.getValue().toString().replaceAll("@.*", ""))
                    .findFirst()
                    .orElseThrow();
            UserDetails userDetails = authService.loadUserByUsername(login);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    login,
                    userDetails.getPassword(),
                    userDetails.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            response.sendRedirect("/users"); //OK
        };
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
