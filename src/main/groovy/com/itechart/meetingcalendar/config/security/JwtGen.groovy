package com.itechart.meetingcalendar.config.security

import com.itechart.meetingcalendar.model.user.entity.User
import com.itechart.meetingcalendar.model.user.repository.UserRepository
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class JwtGen {

    @Autowired
    private UserRepository userRepository

    @Autowired
    private PasswordEncoder passwordEncoder

    String generate(User user) {

        User base = userRepository.findUserByEmailAndActive(user.getEmail(), true)
        if (base == null || user.getPassword() == null) {
            return null
        }
        Claims claims = Jwts.claims()
                .setSubject(base.getId().toString())
        claims.put("username", String.valueOf(base.getUsername()))


        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, "secret-key")
                .compact()
    }


}
