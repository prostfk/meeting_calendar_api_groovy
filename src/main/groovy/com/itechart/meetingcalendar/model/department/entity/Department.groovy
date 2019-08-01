package com.itechart.meetingcalendar.model.department.entity

import com.itechart.meetingcalendar.model.base.BaseEntity
import com.itechart.meetingcalendar.model.base.SafeDeleted
import groovy.transform.Canonical
import groovy.transform.TupleConstructor

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Canonical
@TupleConstructor
@Entity
class Department extends SafeDeleted {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id
    String name


}
