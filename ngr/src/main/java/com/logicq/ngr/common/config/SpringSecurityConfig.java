package com.logicq.ngr.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.logicq.ngr.service.security.TokenAuthenticationService;
import com.logicq.ngr.service.security.UserService;

@Configuration
@EnableWebSecurity
@Order(2)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final TokenAuthenticationService tokenAuthenticationService;
    private final String token="tooManySecrets";
  
    public SpringSecurityConfig() {
        super(true);
        this.userService = new UserService();
        tokenAuthenticationService = new TokenAuthenticationService(token, userService);
    }
    
 
  
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .exceptionHandling().and()
                .anonymous().and()
                .servletApi().and()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/**/*.html").permitAll()
                .antMatchers("/**/*.png").permitAll()
                .antMatchers("/**/*.css").permitAll()
                .antMatchers("/**/*.js").permitAll()
                .antMatchers("/**/*.properties").permitAll()
                .antMatchers("/login.html").permitAll()
                .antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                // Allow anonymous logins
                .antMatchers("/api/login").permitAll()

                // All other request need to be authenticated
                .anyRequest().authenticated().and()
                
                // Custom Token based authentication based on the header previously given to the client
                .addFilterBefore(new RestAuthenticationFilter(userService,tokenAuthenticationService), UsernamePasswordAuthenticationFilter.class);
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    @Override
    public UserService userDetailsService() {
        return userService;
    }

    @Bean
    public TokenAuthenticationService tokenAuthenticationService() {
        return tokenAuthenticationService;
    }
}
