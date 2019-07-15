package com.itechart.meetingcalendar.controller

import com.itechart.meetingcalendar.model.user.dto.IUserDto
import com.itechart.meetingcalendar.model.user.entity.User
import com.itechart.meetingcalendar.model.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

import javax.validation.Valid

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NO_CONTENT

@RestController("/api/users")
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

    @GetMapping
    Page<IUserDto> getUsers(@RequestParam String fullName, @RequestParam Pageable pageable) {
        userService.findByFullName "%${fullName}%".toString(), pageable
    }

}
