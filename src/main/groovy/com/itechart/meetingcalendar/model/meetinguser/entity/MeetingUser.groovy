package com.itechart.meetingcalendar.model.meetinguser.entity


import com.itechart.meetingcalendar.model.base.SafeDeleted
import groovy.transform.Canonical
import groovy.transform.TupleConstructor

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
@Canonical
@TupleConstructor
class MeetingUser extends SafeDeleted {

    @Id
    @GeneratedValue
    Long id
    Long meetingId
    Long userId

}
