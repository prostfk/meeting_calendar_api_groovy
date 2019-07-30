package com.itechart.meetingcalendar.config.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetail implements UserDetails {


    private Long id
    private String username
    private String token
    private Collection<? extends GrantedAuthority> authorities

    UserDetail(String username, String token, Long id, Collection<? extends GrantedAuthority> authorities) {
        this.username = username
        this.token = token
        this.id = id
        this.authorities = authorities
    }

    @Override
    Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities
    }

    @Override
    String getPassword() {
        return null
    }

    @Override
    String getUsername() {
        return username
    }

    @Override
    boolean isAccountNonExpired() {
        return true
    }

    @Override
    boolean isAccountNonLocked() {
        return true
    }

    @Override
    boolean isCredentialsNonExpired() {
        return true
    }

    @Override
    boolean isEnabled() {
        return true
    }

    String getToken() {
        return token
    }

    Long getId() {
        return id
    }

}
