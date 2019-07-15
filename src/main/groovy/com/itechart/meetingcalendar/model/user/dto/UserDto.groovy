package com.itechart.meetingcalendar.model.user.dto

import groovy.transform.Canonical
import groovy.transform.TupleConstructor

@Canonical
@TupleConstructor
class UserDto implements IUserDto{

    Long id
    String firstName
    String lastName

}
