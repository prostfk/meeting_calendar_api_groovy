package com.itechart.meetingcalendar.model.user.entity

import com.itechart.meetingcalendar.model.base.BaseEntity
import groovy.transform.Canonical
import groovy.transform.TupleConstructor

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.NotBlank

@Entity
@Canonical
@TupleConstructor
class User implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id
    @NotBlank
    String firstName
    @NotBlank
    String lastName
    String patronymic
    @NotBlank
    String username
    String password
    String department
    Integer room

}
