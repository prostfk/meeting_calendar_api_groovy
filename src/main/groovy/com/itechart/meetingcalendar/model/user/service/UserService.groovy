package com.itechart.meetingcalendar.model.user.service

import com.itechart.meetingcalendar.exceptions.BadRequestException
import com.itechart.meetingcalendar.exceptions.CustomBodyResponseException
import com.itechart.meetingcalendar.model.base.BaseService
import com.itechart.meetingcalendar.model.user.dto.IUserDto
import com.itechart.meetingcalendar.model.user.dto.UserDto
import com.itechart.meetingcalendar.model.user.entity.User
import com.itechart.meetingcalendar.model.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService extends BaseService<User> {

    private UserRepository repository
    private PasswordEncoder passwordEncoder

    @Autowired
    UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        super(repository)
        this.repository = repository
        this.passwordEncoder = passwordEncoder
    }

    User findByUsername(String username) {
        repository.findByUsername username
    }

    Page<IUserDto> findByFullName(String fullName, Pageable pageable) {
        repository.findByFullName fullName, pageable
    }

    User findByUsernameOrEmail(String usernameOrEmail) {
        repository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
    }

    User registerUser(User user) {
        def byUsername = findByUsernameOrEmail user.username
        if (byUsername) {
            if (byUsername.username == user.username) {
                throw new CustomBodyResponseException("username", "User with this username already exists", 400)
            } else if (byUsername.email == user.email) {
                throw new CustomBodyResponseException("email", "User with this email already exists", 400)
            }
        }
        if (user.password.length() < 6 || user.password.length() > 20) {
            throw new CustomBodyResponseException("password", "Password must be between 6 and 20 chars", 400)
        }
        user.id = null
        user.password = passwordEncoder.encode(user.password)
        save(user)
    }

    User findByEmail(String email) {
        repository.findByEmail email
    }
}
