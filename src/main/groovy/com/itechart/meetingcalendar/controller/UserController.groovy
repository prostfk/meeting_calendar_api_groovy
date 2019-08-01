package com.itechart.meetingcalendar.controller

import com.itechart.meetingcalendar.model.user.dto.IUserDto
import com.itechart.meetingcalendar.model.user.dto.UserDto
import com.itechart.meetingcalendar.model.user.dto.UserProfileDto
import com.itechart.meetingcalendar.model.user.entity.User
import com.itechart.meetingcalendar.model.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

import javax.validation.Valid

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NO_CONTENT

@RestController
@RequestMapping("/api/users")
class UserController {

    @Autowired
    private UserService userService

    @GetMapping("/{id}")
    User getById(@PathVariable Long id) {
        userService.findById id
    }

    @PutMapping
    void putUser(@Valid User user) {
        userService.update user
    }

    @ResponseStatus(CREATED)
    @PostMapping
    void postUser(User user) {
        userService.save user
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/{id}")
    void deleteUser(@PathVariable Long id) {
        userService.delete new User(id)
    }

    @GetMapping("/profile")
    UserProfileDto getProfile() {
        def name = SecurityContextHolder.context.authentication.name
        userService.findUserProfileInfo(name)
    }

    @PutMapping("/profile")
    void putProfile(@RequestBody UserProfileDto dto) {
        def name = SecurityContextHolder.context.authentication.name
        def current = userService.findByUsername name
        current.firstName = dto.firstName
        current.lastName = dto.lastName
        current.department = dto.department
        current.room = dto.room
        userService.update current
    }

    @GetMapping
    Page<IUserDto> getUsers(@RequestParam String fullName, @RequestParam Pageable pageable) {
        userService.findByFullName "%${fullName}%".toString(), pageable
    }

}
