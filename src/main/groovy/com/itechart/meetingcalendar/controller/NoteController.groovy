package com.itechart.meetingcalendar.controller

import com.itechart.meetingcalendar.exceptions.BadRequestException
import com.itechart.meetingcalendar.exceptions.ForbiddenException
import com.itechart.meetingcalendar.model.note.entity.Note
import com.itechart.meetingcalendar.model.note.service.NoteService
import com.itechart.meetingcalendar.model.user.entity.User
import com.itechart.meetingcalendar.model.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NO_CONTENT

@RestController
@RequestMapping("/api/notes")
class NoteController {

    @Autowired
    private NoteService noteService

    @Autowired
    private UserService userService

    @GetMapping
    Page<Note> getNotes(Pageable pageable) {
        def name = SecurityContextHolder.context.authentication.name
        def user = userService.findByUsername name
        noteService.findByUserIdAndActive user.id, pageable, true
    }

    @ResponseStatus(CREATED)
    @PostMapping
    void postNotes(@RequestBody Note note) {
        def name = SecurityContextHolder.context.authentication.name
        def user = userService.findByUsername name
        note.userId = user.id
        note.active = true
        noteService.save note
    }

    @PutMapping
    void putNotes(@RequestBody Note note) {
        if (!note.id) {
            throw new BadRequestException("Provide id field")
        }
        def name = SecurityContextHolder.context.authentication.name
        def user = userService.findByUsername name
        def record = noteService.findById note.id
        if (!record || !record.active) {
            throw new BadRequestException("No such note")
        }
        if (record.userId != user.id) {
            throw new ForbiddenException("You have no access to this record")
        }
        note.userId = record.userId
        noteService.update note
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/{id}")
    void deleteNotes(@PathVariable Long id) {
        def name = SecurityContextHolder.context.authentication.name
        def record = noteService.findById(id)
        if (!record || !record.active) {
            throw new BadRequestException("No such note")
        }
        def user = userService.findByUsername name
        if (user.id != record.userId) {
            throw new ForbiddenException("You have no access to this record")
        }
        record.active = false
        noteService.update record
    }

}
