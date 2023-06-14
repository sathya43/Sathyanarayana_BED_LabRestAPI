package com.college.fest.debate.event.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
    private final CustomAuthenticationFailureHandler authenticationFailureHandler;
    

    private final DataSource dataSource;
    
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(CustomAuthenticationFailureHandler authenticationFailureHandler,DataSource dataSource,PasswordEncoder passwordEncoder) {
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.dataSource = dataSource;
        this.passwordEncoder = passwordEncoder;
    }
    
 
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
        .withDefaultSchema()
        .dataSource(dataSource)
        .passwordEncoder(passwordEncoder)
        .withUser("admin").password(passwordEncoder.encode("admin")).roles("ADMIN")
        .and()
        .withUser("user").password(passwordEncoder.encode("user")).roles("USER");
        

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	   http.authorizeRequests()
    	   .antMatchers("/").authenticated() 
           .antMatchers("/students/add","/students/view/**").hasAnyRole("USER", "ADMIN")
           .antMatchers("/students/list").hasAnyRole("USER", "ADMIN")
           .antMatchers("/students/edit/**", "/students/delete/**").hasRole("ADMIN")
           .anyRequest().authenticated()
           .and()
           .formLogin()
           .and()
           .logout().logoutSuccessUrl("/").permitAll();
    }
    

}