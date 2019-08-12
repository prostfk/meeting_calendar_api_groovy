package com.itechart.meetingcalendar.model.user.dto

import groovy.transform.Canonical
import groovy.transform.TupleConstructor

@Canonical
@TupleConstructor
class FindUserDto {

    String query
    String department
    Integer room

}
