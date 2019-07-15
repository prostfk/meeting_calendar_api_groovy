package com.itechart.meetingcalendar.controller

import com.itechart.meetingcalendar.model.meeting.entity.Meeting
import com.itechart.meetingcalendar.model.meeting.service.MeetingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

import javax.validation.Valid

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NO_CONTENT

@RestController("/api/meetings")
class MeetingController {

    @Autowired
    private MeetingService meetingService

    @GetMapping("/{id}")
    Meeting getById(@PathVariable Long id) {
        meetingService.findById id
    }

    @PutMapping
    void putMeeting(@Valid Meeting meeting) {
        meetingService.update meeting
    }

    @ResponseStatus(CREATED)
    @PostMapping
    void postMeeting(@Valid Meeting meeting) {
        meetingService.save meeting
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/{id}")
    void deleteMeeting(@PathVariable Long id) {
        meetingService.delete new Meeting(id)
    }

    @GetMapping
    Page<Meeting> getMeetings(Pageable pageable) {
        //todo get current user id
        meetingService.findAllowedMeetings 1l, pageable
    }


}
