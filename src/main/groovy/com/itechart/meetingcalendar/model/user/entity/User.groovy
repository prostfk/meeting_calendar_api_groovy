package com.itechart.meetingcalendar.model.user.entity

import com.itechart.meetingcalendar.model.base.BaseEntity
import com.itechart.meetingcalendar.model.base.SafeDeleted
import groovy.transform.Canonical
import groovy.transform.TupleConstructor

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.NotBlank

@Entity
@Table(name = 'users')
@Canonical
@TupleConstructor
class User extends SafeDeleted {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id
    @NotBlank
    String firstName
    @NotBlank
    String lastName
    String patronymic
    @NotBlank
    String email
    @NotBlank
    String username
    @NotBlank
    String password
    String department
    Integer room
    Boolean active

}
