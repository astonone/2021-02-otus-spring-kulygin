package ru.otus.kulygin.facadegateway.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    public SecurityConfiguration(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/common-api/**").permitAll()
                .antMatchers("/assets/themes/*.css", "/assets/i18n/*.json", "/assets/*.png", "/assets/image/*.png", "/assets/image/actions/*.svg", "/assets/image/actions/*.png", "/*.js", "/*.css", "/*.html").permitAll()
                .antMatchers("/common-api/user-service/interviewer/login").permitAll()
                .antMatchers(HttpMethod.POST, "/common-api/criteria-service/interview-template-criteria/").hasRole("DEVELOPER")
                .antMatchers(HttpMethod.POST, "/common-api/criteria-service/interview-template-criteria/job/import-criterias").hasRole("DEVELOPER")
                .antMatchers(HttpMethod.DELETE, "/common-api/criteria-service/interview-template-criteria/*").hasRole("DEVELOPER")
                .antMatchers(HttpMethod.POST, "/common-api/template-service/interview-template/").hasRole("DEVELOPER")
                .antMatchers(HttpMethod.DELETE, "/common-api/template-service/interview-template/*").hasRole("DEVELOPER")
                .antMatchers(HttpMethod.POST, "/common-api/template-service/interview-template/*/criteria").hasRole("DEVELOPER")
                .antMatchers(HttpMethod.DELETE, "/common-api/template-service/interview-template/*/criteria/*").hasRole("DEVELOPER")
                .antMatchers(HttpMethod.DELETE, "/common-api/user-service/interviewer/*").hasRole("DEVELOPER")
                .antMatchers(HttpMethod.POST, "/common-api/user-service/interviewer/").permitAll()
                .anyRequest()
                .fullyAuthenticated()
                .and()
                .httpBasic()
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

}
