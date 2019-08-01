package com.itechart.meetingcalendar.model.user.dto

import com.itechart.meetingcalendar.model.user.entity.User
import groovy.transform.Canonical
import groovy.transform.TupleConstructor

import javax.validation.Valid

@TupleConstructor
@Canonical
class RegisterUserDto {

    @Valid
    User user
    String token

}
