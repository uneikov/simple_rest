package com.uran;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true)
@ComponentScan(basePackages = "com.uran")
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class RestSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    //@Autowired
    //private SecurityHandler securityHandler;
    
   /* @Bean(name = "passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }*/
    
    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService userDetailsService;
    
    @Autowired
    public void ConfigureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //auth.authenticationProvider(authenticationProvider());
        auth.userDetailsService(this.userDetailsService).passwordEncoder(this.passwordEncoder);
    }
    
    
    /*@Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }*/
    /*@Bean
    public Http401AuthenticationEntryPoint securityException401EntryPoint(){
        
        return new Http401AuthenticationEntryPoint("Bearer realm=\"webrealm\"");
    }*/
    
    /*@Autowired
    private Http401AuthenticationEntryPoint authEntrypoint;*/
   /* @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources*//**");
                //.antMatchers("/webjars*//**");
    }*/
    /*@Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }*/
    /*@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().fullyAuthenticated().and().csrf().disable();
        http.httpBasic().authenticationEntryPoint(authenticationEntryPoint());
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());
        
    }*/
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        /*http.authorizeRequests()
                .antMatchers(new String[] { "/", "/login", "/error",
                        "/loginFailed", "/static*//**" })
                .permitAll();*/
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/horses/search/names").hasAnyRole("ADMIN", "USER")
                .antMatchers("/api/horses/**").hasRole("ADMIN")
                .antMatchers("/api/races/**").hasRole("ADMIN")
                .antMatchers("/api/users/**").hasRole("ADMIN")
                .antMatchers("/api/stakes/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/api/accounts/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/api/**").hasAnyRole("ADMIN", "STATION", "USER")
                .anyRequest().authenticated()
                .and()
                    .securityContext()
                .and()
                    .httpBasic()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .maximumSessions(1);
        
        //.authenticationProvider((AuthenticationProvider) userService)
        //.exceptionHandling().authenticationEntryPoint(authEntrypoint).and()
        // @formatter:on
    }
    
}
/* @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService);
    }*/
    
    /*@Bean(name = "authProvider")
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }*/