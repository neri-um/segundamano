package pasarela.zuul.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private SecuritySuccessHandler successHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().httpBasic().disable()
            .authorizeRequests()
                .antMatchers("/auth/login").permitAll()
                .anyRequest().authenticated()
            .and()
            .oauth2Login().successHandler(this.successHandler)
            .and()
            .sessionManagement()
            	.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

        http.addFilterBefore(jwtRequestFilter,
            UsernamePasswordAuthenticationFilter.class);
    }
}