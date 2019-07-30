package com.itechart.meetingcalendar.controller

import com.itechart.meetingcalendar.config.security.JwtGen
import com.itechart.meetingcalendar.exceptions.BadRequestException
import com.itechart.meetingcalendar.exceptions.CustomBodyResponseException
import com.itechart.meetingcalendar.model.registrationtoken.entity.Token
import com.itechart.meetingcalendar.model.registrationtoken.service.TokenService
import com.itechart.meetingcalendar.model.user.dto.UserAuthDto
import com.itechart.meetingcalendar.model.user.entity.User
import com.itechart.meetingcalendar.model.user.service.UserService
import com.itechart.meetingcalendar.service.EmailService
import com.itechart.meetingcalendar.service.EmailTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*

import javax.validation.Valid
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

import static com.itechart.meetingcalendar.model.registrationtoken.entity.TokenType.*
import static org.springframework.http.HttpStatus.CREATED

@RestController
class AuthController {

    @Autowired
    private UserService userService

    @Autowired
    private TokenService tokenService

    @Autowired
    private PasswordEncoder passwordEncoder

    @Autowired
    private EmailService emailService

    @Autowired
    private JwtGen jwtGen

    @Value('${application.url}')
    private String appUrl

    @PostMapping("/auth")
    def postAuth(@NotNull UserAuthDto user) {
        def baseUser = userService.findByUsernameOrEmail user.username
        if (!baseUser) {
            throw new BadRequestException("No such user")
        }
        if (!passwordEncoder.matches(user.password, baseUser.password)) {
            throw new CustomBodyResponseException("password","Invalid password", 400)
        }
        def token = jwtGen.generate(baseUser)
        "{\"token\": \"${token}\"}"
    }

    @GetMapping("/registration")
    User getInviteInfo(@NotBlank String token) {
        def baseToken = tokenService.findByValueAndType token, REGISTRATION
        if (!baseToken || !baseToken.active) {
            throw new BadRequestException("No such token")
        }
        userService.findById baseToken.userId
    }

    @ResponseStatus(CREATED)
    @PostMapping("/registration")
    void postRegistration(@Valid User user, String token) {
        if (token) {
            def baseToken = tokenService.findByValueAndType token, REGISTRATION
            if (baseToken && baseToken.active) {
                baseToken.active = false
                user.active = true
            } else {
                throw new CustomBodyResponseException("token", "Invalid token", 400)
            }
            userService.registerUser user
        } else {
            user.active = false
            User savedUser = userService.registerUser user
            Token regToken = new Token(userId: savedUser.id, type: VALIDATE_EMAIL, active: true)
            Token savedToken = tokenService.save(regToken)
            Map<String, String> params = [link: "${appUrl}/auth?token=${savedToken.value}"]
            emailService.sendTemplate savedUser.email, "Registration", EmailTemplate.REGISTRATION, params
        }
    }

    @PostMapping("/password")
    void postPassword(String email) {
        def user = userService.findByEmail email
        if (!user) {
            throw new CustomBodyResponseException("email", "No user with such email", 400)
        }
        Token token = new Token(userId: user.id, type: PASSWORD_RESET, active: true)
        def savedToken = tokenService.save token
        Map<String, String> params = [link: "${appUrl}/password?token=${savedToken.value}"]
        emailService.sendTemplate user.email, "Password reset", EmailTemplate.PASSWORD_RESET, params
    }

    @PutMapping("/password")
    void putPassword(@Email String email, @NotBlank String token, @Size(min = 6, max = 20) String password) {
        def baseToken = tokenService.findByValue token
        if (!baseToken) {
            throw new BadRequestException("No such token")
        }
        def baseUser = userService.findByEmail email
        if (!baseUser) {
            throw new CustomBodyResponseException("email","No user with this email", 400)
        }
        if (baseToken.userId != baseUser.id) {
            throw new CustomBodyResponseException("email","Invalid email", 400)
        }
        baseUser.password = passwordEncoder.encode(password)
        userService.update(baseUser)
    }

}
