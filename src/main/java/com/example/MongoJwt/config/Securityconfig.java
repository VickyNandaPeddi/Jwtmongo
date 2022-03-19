package com.example.MongoJwt.config;

import com.example.MongoJwt.filter.Jwtfilter;
import com.example.MongoJwt.service.CustomUserdetailsservice;
import com.example.MongoJwt.util.Jwtutil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Securityconfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomUserdetailsservice customUserdetailsservice;
    @Autowired
    private Jwtutil jwtutil;
    @Autowired
    private Jwtfilter jwtrequestfilter;

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private Jwtauthenticationentypoint jwtauthenticationentypoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and().csrf().disable().formLogin();
        ;
        http.authorizeRequests().antMatchers("/generatetoken")
                .permitAll().anyRequest().authenticated().and().exceptionHandling().authenticationEntryPoint(jwtauthenticationentypoint);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtrequestfilter, UsernamePasswordAuthenticationFilter.class);


    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserdetailsservice).passwordEncoder(getEncoder());
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
