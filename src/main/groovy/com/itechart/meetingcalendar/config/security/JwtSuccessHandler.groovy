package com.itechart.meetingcalendar.config.security

import com.itechart.meetingcalendar.model.user.entity.User
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.AuthenticationSuccessHandler

import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtSuccessHandler implements AuthenticationSuccessHandler {


    @Override
    void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        String token = httpServletRequest.getHeader("Authorization")
        User validate = new JwtVal().validate(token.split(" ")[1])
        List<GrantedAuthority> authorities = new ArrayList<>()
//        authorities.add(new SimpleGrantedAuthority(validate.getUserRole().name()))
        JwtAuthToken jwtAuthToken = new JwtAuthToken(token, validate.username, "", authorities)
        jwtAuthToken.setDetails(jwtAuthToken.getCredentials())
        SecurityContextHolder.getContext().setAuthentication(jwtAuthToken)

    }

}
