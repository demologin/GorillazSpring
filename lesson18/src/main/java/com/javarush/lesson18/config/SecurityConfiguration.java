package com.javarush.lesson18.config;


import com.javarush.lesson18.entity.Role;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {


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
                .logout(logoutConfig -> logoutConfig
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout","GET"))
                        .logoutSuccessUrl("/login?logout=true"))
                .build();
    }

//    @Bean
//    public InMemoryUserDetailsManager inMemoryUserDetailsManager(SecurityProperties properties,
//                                                                 ObjectProvider<PasswordEncoder> passwordEncoder) {
//        SecurityProperties.User user = properties.getUser();
//        UserDetails[] users = {
//                User.withUsername("Carl")
//                        .password("{bcrypt}$2a$10$7FAMPKijZlIvQlzFSJUqMe4BQGUXp3OzCZXFY/kboSYQ78EinaD1C")
//                        .roles("ADMIN")
//                        .build()
//        };
//        return new InMemoryUserDetailsManager(users);
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
