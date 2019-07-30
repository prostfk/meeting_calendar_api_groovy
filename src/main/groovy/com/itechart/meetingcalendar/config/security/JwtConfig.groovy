package com.itechart.meetingcalendar.config.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
@Order(1)
class JwtConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private JwtAuthProvider authenticationProvider

    @Autowired
    private JwtAuthenticationEntryPoint entryPoint

    @Bean
    AuthenticationManager authenticationManager() {
        return new ProviderManager(Collections.singletonList(authenticationProvider))
    }

    //create a custom filter
    @Bean
    JwtAuthFilter authTokenFilter() {

        JwtAuthFilter filter = new JwtAuthFilter()
        filter.setAuthenticationManager(authenticationManager())
        filter.setAuthenticationSuccessHandler(new JwtSuccessHandler())
        return filter

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource()
        final CorsConfiguration config = new CorsConfiguration()

        config.addAllowedOrigin("*")
        config.addAllowedHeader("*")
        config.addAllowedMethod("GET")
        config.addAllowedMethod("PUT")
        config.addAllowedMethod("POST")
        config.addAllowedMethod("DELETE")
        config.setAllowCredentials(true)
        source.registerCorsConfiguration("/**", config)
        http.csrf().disable()
                .authorizeRequests().antMatchers("**/**").authenticated()
                .and().cors().configurationSource(source).and()
                .exceptionHandling().authenticationEntryPoint(entryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class)
        http.headers().cacheControl()
        http.cors()
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder()
    }



}
