package com.itechart.meetingcalendar.config.security

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class JwtAuthToken extends UsernamePasswordAuthenticationToken {


    private String token
    private String username

    JwtAuthToken(String token) {
        super(null, null)
        this.token = token
    }

    JwtAuthToken(String token, Object principal, Object credentials, List<GrantedAuthority> grantedAuthorities) {
        super(principal, credentials, grantedAuthorities)
        username = (String) principal
        this.token = token
    }

    String getToken() {
        return token
    }

    void setToken(String token) {
        this.token = token
    }

    void setUsername(String username) {
        this.username = username
    }

    void setRole(String role) {
        this.role = role
    }

    @Override
    Object getCredentials() {
        return null
    }

    @Override
    Object getPrincipal() {
        return username
    }



}
