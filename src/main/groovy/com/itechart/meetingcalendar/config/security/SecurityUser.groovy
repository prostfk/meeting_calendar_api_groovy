package com.itechart.meetingcalendar.config.security

import com.itechart.meetingcalendar.model.user.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class SecurityUser implements UserDetails {

    private User user

    SecurityUser(User user) {
        this.user = user
    }

    @Override
    Collection<? extends GrantedAuthority> getAuthorities() {
        return null
    }

    @Override
    String getPassword() {
        user.password
    }

    @Override
    String getUsername() {
        user.username
    }

    @Override
    boolean isAccountNonExpired() {
        false
    }

    @Override
    boolean isAccountNonLocked() {
        false
    }

    @Override
    boolean isCredentialsNonExpired() {
        false
    }

    @Override
    boolean isEnabled() {
        true
    }
}
