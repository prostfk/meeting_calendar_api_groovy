package com.itechart.meetingcalendar.config

import com.itechart.meetingcalendar.config.security.SecurityUser
import com.itechart.meetingcalendar.model.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService{

    @Autowired
    private UserService userService

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        def user = userService.findByUsername username
        if (user) {
            def ud = new SecurityUser(user)
            ud
        } else {
            throw new UsernameNotFoundException("No such user")
        }
    }
}
