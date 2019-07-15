package com.itechart.meetingcalendar.model.meeting.entity

import com.itechart.meetingcalendar.model.base.BaseEntity
import com.itechart.meetingcalendar.model.user.entity.User
import groovy.transform.Canonical
import groovy.transform.TupleConstructor

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
@Canonical
@TupleConstructor
class Meeting implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id
    @NotBlank
    String name
    @NotNull
    Date date
    Date cretedDate
    @NotNull
    Long creatorId
    List<User> users

}