package com.ethan.crmsystem.config;

import com.ethan.crmsystem.auth.UserDetailsServiceImpl;
import com.ethan.crmsystem.auth.WhaleAuthenticationProvider;
import com.ethan.crmsystem.jwt.JwtAuthenticationConfig;
import com.ethan.crmsystem.jwt.JwtTokenAuthenticationFilter;
import com.ethan.crmsystem.jwt.JwtUsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

import javax.servlet.http.HttpServletResponse;


/**
 * Description:网关安全配置，用于过滤访问请求
 *
 * @author <a href="mailto:chencq@hzwesoft.com">chencq</a>
 * @version $Id: SecurityConfig.java, 2018/9/6 15:10 $
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private JwtAuthenticationConfig config;

    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public JwtAuthenticationConfig jwtConfig() {
        return new JwtAuthenticationConfig();
    }

    @Bean
    public WhaleAuthenticationProvider authenticationProvider() {
        WhaleAuthenticationProvider result = new WhaleAuthenticationProvider();
        result.setUserDetailsService(userDetailsService);
        result.setPasswordEncoder(new BCryptPasswordEncoder(4));

        return result;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().and().csrf().disable().logout().disable().formLogin().disable().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().exceptionHandling()
                .authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .addFilterAfter(new JwtTokenAuthenticationFilter(config),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new JwtUsernamePasswordAuthenticationFilter(config, authenticationManager()),
                        UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests().antMatchers(config.getUrl()).permitAll()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .anyRequest().authenticated();
    }

    @Autowired
    public void setConfig(JwtAuthenticationConfig config) {
        this.config = config;
    }
}
