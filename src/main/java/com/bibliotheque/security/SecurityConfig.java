package com.bibliotheque.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private DataSource dataSource;



    @Override

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		BCryptPasswordEncoder bcpe=new BCryptPasswordEncoder();
		auth.inMemoryAuthentication().withUser("admin").password(bcpe.encode("1234")).roles("ADMIN");
		//auth.inMemoryAuthentication().withUser("user").password(bcpe.encode("1234")).roles("USER");
		auth.inMemoryAuthentication().passwordEncoder(bcpe);

        /*auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select user_name as principal, password as credentials, active from user where user_name=?")
                .authoritiesByUsernameQuery("select user_name as principal, nom_role as role from user, role  where user_name=? and role.id_role=user.id_role ")
                .rolePrefix("ROLE_")
                .passwordEncoder(getBCPE());*/
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
// commentaire
       /* http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
        //http.formLogin().loginPage("/login");
        http.formLogin();
        http.authorizeRequests().anyRequest().hasRole("ADMIN");
        //http.authorizeRequests().antMatchers("/user/*").hasRole("USER");
        //http.exceptionHandling().accessDeniedPage("/403");

        http.logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout");*/
        /*http.csrf().disable();
        http.authorizeRequests().anyRequest().permitAll();*/

        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/login/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthentificationFilter(authenticationManager()))
                .addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);


    }
    @Bean
    BCryptPasswordEncoder getBCPE() {
        return new BCryptPasswordEncoder();
    }
}
