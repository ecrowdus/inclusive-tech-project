package com.prefchefs.findfood.config;

import com.prefchefs.findfood.service.PrefChefsUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private DataSource dataSource;

    @Autowired
    @Qualifier("userDetailsService")
    private PrefChefsUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/css/**", "/img/**", "/js/**", "/", "/login").permitAll()
                .antMatchers("/**").hasRole("USER")
                .and()
                .formLogin()
                .loginPage("/login")//.failureUrl("/login-error");
                .and()
                .logout();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
//                .jdbcAuthentication()
//                .dataSource(dataSource)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
//        auth
//                .inMemoryAuthentication()
//                .withUser("user").password(passwordEncoder().encode("password")).roles("USER");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}




//package com.prefchefs.findfood;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import javax.sql.DataSource;
//
//@EnableWebSecurity
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
////    @Autowired
////    private DataSource dataSource;
//
//    @Override
//     protected void configure(HttpSecurity httpSecurity) throws Exception {
////        httpSecurity.authorizeRequests()
////            .antMatchers("/login/**")
////            .permitAll()
////            .anyRequest()
////            .authenticated()
////            .and()
////            .formLogin();
////
////        httpSecurity.csrf()
////            .ignoringAntMatchers("/login/**");
////        httpSecurity.headers()
////            .frameOptions()
////            .sameOrigin();
//        httpSecurity
//                .authorizeRequests()
//                .antMatchers("/css/**", "/index").permitAll()
//                .antMatchers("/user/**").hasRole("USER")
//                .and()
//                .formLogin()
//                .loginPage("/login").failureUrl("/login-error");
//    }
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
////        auth
////            .jdbcAuthentication()
////            .dataSource(dataSource);
//        auth
//                .inMemoryAuthentication()
//                .withUser("user").password("password").roles("USER");
//
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//}
