package com.blog1.config;

import com.blog1.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration //responsible for defining and configuring the security settings(own configuration)
@EnableWebSecurity //to enable the web security features
@EnableGlobalMethodSecurity(prePostEnabled = true) //to apply security measures to individual methods or at the class level
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable() //protect against CSRF attacks

            .authorizeRequests() //configures the authorization rules for the requests

                .antMatchers(HttpMethod.GET, "/api/**").permitAll() //Allows access to /api/** without authentication

                .antMatchers("/api/auth/**").permitAll() //Allows access to /api/auth/** without authentication

                .anyRequest().authenticated() //Requires authentication for all other requests

            .and().httpBasic(); //enables HTTP Basic Authentication for securing an application's endpoints
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    //Configure in-memory authentication for development/testing purposes:-
   /* @Override
	@Bean
	protected UserDetailsService userDetailsService() {

	UserDetails ramesh = User.builder().username("ramesh").password(passwordEncoder()
	.encode("password")).roles("USER").build();

	UserDetails admin = User.builder().username("admin").password(passwordEncoder()
	.encode("admin")).roles("ADMIN").build();

	return new InMemoryUserDetailsManager(ramesh, admin);
	}*/

}

