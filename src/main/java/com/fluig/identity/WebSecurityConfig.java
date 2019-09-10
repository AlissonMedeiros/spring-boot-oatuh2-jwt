package com.fluig.identity;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        LoginUrlAuthenticationEntryPoint endPoint = new LoginUrlAuthenticationEntryPoint("/login");
        endPoint.setUseForward(true);
        http.sessionManagement()
                .and()
                .authorizeRequests()
                .antMatchers("/login", "/oauth/authorize", "/oauth/confirm_access")
                .permitAll()
                .and()
                .authorizeRequests()
                .anyRequest()
                .permitAll()
                .and()
                .httpBasic()
                .authenticationEntryPoint(endPoint)
                .and().formLogin().loginPage("/login").permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource);
    }
}
