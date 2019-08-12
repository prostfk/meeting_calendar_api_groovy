package com.itechart.meetingcalendar.model.user.dto

import groovy.transform.Canonical
import groovy.transform.TupleConstructor

@Canonical
@TupleConstructor
class UserDto {

    Long id
    String firstName
    String lastName
    String department
    String email
    String avatar
    Integer room

}
