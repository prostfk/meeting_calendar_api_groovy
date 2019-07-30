package com.itechart.meetingcalendar.config.security

import com.itechart.meetingcalendar.model.user.entity.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class JwtVal {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtVal.class)

    static User validate(String token) {

        User jwtUser = null
        try {
            Claims body = Jwts.parser().setSigningKey("secret-key").parseClaimsJws(token).getBody()
            if (body != null) {
                String username = body.get("username", String.class)
                jwtUser = new User(username: username)
            }
        } catch (MalformedJwtException e) {
            LOGGER.info("Empty or invalid token: " + e.getMessage())
        } catch (SignatureException e) {
            LOGGER.warn("Someone tried to change token in header. Token value: " + token)
        } catch (Exception e) {
            LOGGER.warn("Exception in token validation(Maybe, no such user): ", e)
        }
        return jwtUser
    }



}
