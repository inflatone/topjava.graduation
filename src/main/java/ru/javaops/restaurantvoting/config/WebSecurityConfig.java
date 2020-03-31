package ru.javaops.restaurantvoting.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.javaops.restaurantvoting.AuthUser;
import ru.javaops.restaurantvoting.model.Role;
import ru.javaops.restaurantvoting.model.User;
import ru.javaops.restaurantvoting.repository.UserRepository;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    public static final PasswordEncoder PASSWORD_ENCODER = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    private final UserRepository userRepository;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authBuilder) throws Exception {
        authBuilder.userDetailsService(userDetailsService())
                .passwordEncoder(PASSWORD_ENCODER);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/rest/lunches/today").permitAll()
                .antMatchers("/rest/account/register").anonymous()
                .antMatchers("/rest/account").hasRole(Role.USER.name())
                .antMatchers("/rest/today/restaurants").hasRole(Role.USER.name())
                .antMatchers("/rest/**").hasRole(Role.ADMIN.name())
                .and().httpBasic()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable()
        ;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            log.info("Authenticating as {}", email);
            Optional<User> optionalUser = userRepository.findByEmailIgnoreCase(email);
            return new AuthUser(optionalUser.orElseThrow(() -> new UsernameNotFoundException("User '" + email + "' was not found")));
        };
    }
}
