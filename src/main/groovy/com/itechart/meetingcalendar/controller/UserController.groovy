package com.itechart.meetingcalendar.controller

import com.itechart.meetingcalendar.exceptions.BadRequestException
import com.itechart.meetingcalendar.exceptions.CustomResponseException
import com.itechart.meetingcalendar.model.user.dto.FindUserDto
import com.itechart.meetingcalendar.model.user.dto.IUserDto
import com.itechart.meetingcalendar.model.user.dto.UserDto
import com.itechart.meetingcalendar.model.user.dto.UserProfileDto
import com.itechart.meetingcalendar.model.user.dto.UserProfileUpdateDto
import com.itechart.meetingcalendar.model.user.entity.User
import com.itechart.meetingcalendar.model.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

import javax.validation.Valid
import javax.validation.constraints.NotNull

import static com.itechart.meetingcalendar.service.FileService.createFileIfNeed
import static com.itechart.meetingcalendar.service.FileService.saveBase64
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NO_CONTENT

@RestController
@RequestMapping("/api/users")
class UserController {

    @Autowired
    private UserService userService

    @Value('${attachments.path}')
    private String resourcePath

    @GetMapping
    Page<UserDto> getUsers(FindUserDto params, @Valid @NotNull Pageable pageable) {
        userService.findUsers(params, pageable)
    }

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
    void putProfile(@RequestBody UserProfileUpdateDto dto) {
        def name = SecurityContextHolder.context.authentication.name
        def current = userService.findByUsername name
        current.firstName = dto.firstName
        current.lastName = dto.lastName
        current.department = dto.department
        current.room = dto.room
        current.gender = dto.gender
        current.patronymic = dto.patronymic
        userService.update current
    }


    @PutMapping("/avatars")
    void updateAvatar(@NotNull String base64) {
        if (!base64.contains('image/')) {
            throw new BadRequestException("File must be an image")
        }
        def array = base64.split "base64,"
        if (array.length != 2) {
            throw new BadRequestException("Invalid base 64 string")
        }
        def name = SecurityContextHolder.context.authentication.name
        def current = userService.findByUsername name
        def folderPath = "${resourcePath}/avatars/${current.id}"
        createFileIfNeed folderPath, true
        def path = "${folderPath}/avatar.png"
        createFileIfNeed path, false
        def result = saveBase64 array[1], path
        if (!result) {
            throw new CustomResponseException("Cannot save base64", 500)
        }
        current.avatar = "/avatars/${current.id}/avatar.png"
        userService.update(current)
    }
}
