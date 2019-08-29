package com.itechart.meetingcalendar.model.note.entity

import com.itechart.meetingcalendar.model.base.SafeDeleted
import groovy.transform.Canonical

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.Size

@Entity
@Canonical
class Note extends SafeDeleted {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id
    @Size(min = 1, max = 30)
    String title
    @Size(min = 1, max = 5000)
    String note
    Long userId
    Boolean active

}
