package com.itechart.meetingcalendar.model.user.dto

import groovy.transform.Canonical
import groovy.transform.TupleConstructor

@TupleConstructor
@Canonical
trait UserProfileDto {

    String firstName
    String lastName
    String patronymic
    String gender
    String department
    String avatar
    Integer room

}