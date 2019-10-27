package capstone.is4103capstone.configuration;

import capstone.is4103capstone.general.session.ExceptionHandlerFilter;
import capstone.is4103capstone.general.session.SessionKeyAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

import javax.servlet.GenericFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public SessionKeyAuthenticationFilter sessionKeyAuthenticationFilterBean() {
        return new SessionKeyAuthenticationFilter();
    }

    @Bean
    public ExceptionHandlerFilter exceptionHandlerFilterBean() {
        return new ExceptionHandlerFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.anonymous().and().cors().and().csrf().disable()
                .addFilterBefore(exceptionHandlerFilterBean(), SecurityContextPersistenceFilter.class)
                .authorizeRequests()
                .antMatchers("/api/session/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterAfter(sessionKeyAuthenticationFilterBean(), BasicAuthenticationFilter.class)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}

