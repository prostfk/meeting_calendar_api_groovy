package com.itechart.meetingcalendar.config.security

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter

import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthFilter extends AbstractAuthenticationProcessingFilter {

    protected JwtAuthFilter() {
        super("/api/**")
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthFilter.class)

    @Autowired
    private JwtVal jwtVal

    @Override
    Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        String header = httpServletRequest.getHeader("authorization")
        if (header == null) {
            httpServletResponse.sendError(403, "No token in header")
            LOGGER.warn("No token in header")
            return null
        }
        JwtAuthToken token = new JwtAuthToken(header)
        return getAuthenticationManager().authenticate(token)
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult)
        chain.doFilter(request, response)
    }

}
