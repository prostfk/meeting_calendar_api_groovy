package com.itechart.meetingcalendar.config.security

import com.itechart.meetingcalendar.exceptions.BadRequestException
import com.itechart.meetingcalendar.model.user.entity.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component

@Component
class JwtAuthProvider extends AbstractUserDetailsAuthenticationProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthProvider.class)

    @Autowired
    private JwtVal validator

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        //empty
    }

    @Override
    protected UserDetails retrieveUser(String s, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        JwtAuthToken jwtAuthenticationToken = (JwtAuthToken) usernamePasswordAuthenticationToken
        String token = jwtAuthenticationToken.getToken()
        User jwtUser = validator.validate(token.split(" ")[1])

        if (jwtUser == null) {
            LOGGER.warn("JWT Token is incorrect")
            throw new BadRequestException("JWT Token is incorrect")
        }
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("")
        return new UserDetail(jwtUser.username, token, jwtUser.id, grantedAuthorities)

    }

}
