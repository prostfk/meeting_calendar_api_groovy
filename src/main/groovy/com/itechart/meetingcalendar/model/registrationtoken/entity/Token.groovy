package com.itechart.meetingcalendar.model.registrationtoken.entity

import com.itechart.meetingcalendar.model.base.SafeDeleted
import groovy.transform.Canonical
import groovy.transform.TupleConstructor

import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
@Canonical
@TupleConstructor
class Token extends SafeDeleted {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id
    Long userId
    String value
    @Enumerated(value = EnumType.STRING)
    TokenType type
    Boolean active

}
