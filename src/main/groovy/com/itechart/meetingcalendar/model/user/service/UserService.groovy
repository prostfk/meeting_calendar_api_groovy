package com.itechart.meetingcalendar.model.user.service

import com.itechart.meetingcalendar.model.base.BaseService
import com.itechart.meetingcalendar.model.user.dto.IUserDto
import com.itechart.meetingcalendar.model.user.dto.UserDto
import com.itechart.meetingcalendar.model.user.entity.User
import com.itechart.meetingcalendar.model.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class UserService extends BaseService<User> {

    private UserRepository repository

    @Autowired
    UserService(UserRepository repository) {
        super(repository)
        this.repository = repository
    }

    User findByUsername(String username) {
        repository.findByUsername username
    }

    Page<IUserDto> findByFullName(String fullName, Pageable pageable) {
        repository.findByFullName fullName, pageable
    }
}
