package org.id2k1149.project_v10.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable();
        http
            .authorizeRequests()
            .antMatchers("/", "/welcome", "/resources/**", "/registration").permitAll();
        http
            .authorizeRequests()
            .antMatchers(GET, "/api/v1/**")
            .hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
            .and().httpBasic();
        http
            .authorizeRequests()
            .antMatchers(POST, "/api/v1/**")
            .hasAuthority("ROLE_ADMIN")
            .and().httpBasic();
        http
            .authorizeRequests()
            .antMatchers(PUT, "/api/v1/**")
            .hasAuthority("ROLE_ADMIN")
            .and().httpBasic();
        http
            .authorizeRequests()
            .antMatchers(DELETE, "/api/v1/**")
            .hasAuthority("ROLE_ADMIN")
            .and().httpBasic();

        http
            .authorizeRequests()
            .anyRequest()
            .authenticated()
            .and()
            .formLogin()
            .loginPage("/login")
            .permitAll()
            .defaultSuccessUrl("/vote", true)
            .and()
            .logout()
            .permitAll();
          }
}
