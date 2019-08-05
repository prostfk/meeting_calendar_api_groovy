package com.itechart.meetingcalendar.model.meeting.entity

import com.itechart.meetingcalendar.model.base.SafeDeleted
import com.itechart.meetingcalendar.model.user.entity.User
import groovy.transform.Canonical
import groovy.transform.TupleConstructor

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Temporal
import javax.persistence.TemporalType
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

import static javax.persistence.TemporalType.TIME

@Entity
@Canonical
@TupleConstructor
class Meeting extends SafeDeleted {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id
    @Size(min=3, max = 20)
    String title
    @NotNull
    Date date
    @NotNull
//    @Temporal(TIME)
    String startTime
    @NotNull
//    @Temporal(TIME)
    String endTime
    @NotNull
    Integer room
    Date createdDate
    Long creatorId
    Boolean active

}
