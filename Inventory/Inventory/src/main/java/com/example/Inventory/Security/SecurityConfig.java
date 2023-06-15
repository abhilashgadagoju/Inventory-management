package com.example.Inventory.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    MyUserDetailsService myUserDetailsService;
    @Autowired
    JWTFilter myJWTFilter;
    
    /*~~(Migrate manually based on https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter)~~>*/@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	System.out.println("in AuthenticationManagerBuilder");
        auth
        .userDetailsService(myUserDetailsService)
        .passwordEncoder(getPassEncoder());
        System.out.println("in AuthenticationManagerBuilder end");
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
	System.out.println("in HttpSecurity");
        http.csrf(crsf -> crsf.disable())
                .authorizeHttpRequests(requests -> requests
                        .antMatchers("/authenticate").permitAll()
                        .antMatchers("/**").hasAnyAuthority("USER", "ADMIN")
                        .anyRequest().authenticated())
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(myJWTFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin(form->form.loginPage("/login"));
        /*http.build();*/
    /*http.csrf(withDefaults()).disable()
                .authorizeHttpRequests()
                .antMatchers("/authenticate").permitAll()
                .antMatchers("/**").hasAnyAuthority("USER", "ADMIN")
                .anyRequest().authenticated()
                .and().sessionManagement(management -> management
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(withDefaults()).disable();
	http.addFilterBefore(myJWTFilter, UsernamePasswordAuthenticationFilter.class);
        */
        System.out.println("in HttpSecurity end");
    }
    
    
    @Bean
    protected PasswordEncoder getPassEncoder(){
	return NoOpPasswordEncoder.getInstance();
    }
    
    /*~~(Migrate manually based on https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter)~~>*/@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
