package com.itechart.meetingcalendar.controller

import com.itechart.meetingcalendar.model.meeting.entity.Meeting
import com.itechart.meetingcalendar.model.meeting.service.MeetingService
import com.itechart.meetingcalendar.model.user.entity.User
import com.itechart.meetingcalendar.model.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

import javax.persistence.Temporal
import javax.validation.Valid

import static javax.persistence.TemporalType.TIME
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NO_CONTENT

@RestController
@RequestMapping("/api/meetings")
class MeetingController {

    @Autowired
    private MeetingService meetingService

    @Autowired
    private UserService userService

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
    Map<String, Long> postMeeting(@RequestBody @Valid Meeting meeting) {
        def saved = meetingService.save meeting
        def map = new HashMap<String, Long>()
        map.put('id', saved.id)
        map
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/{id}")
    void deleteMeeting(@PathVariable Long id) {
        meetingService.delete new Meeting(id)
    }

    @GetMapping
    Iterable<Meeting> getMeetings(Pageable pageable, @RequestParam(required = false) Date dateStart, @RequestParam(required = false) Date dateEnd, @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date date, @RequestParam(required = false) Integer room, @RequestParam(required = false) String startTime, @RequestParam(required = false) String endTime) {
        if (dateStart && dateEnd) {
            def name = SecurityContextHolder.context.authentication.name
            def user = userService.findByUsername name
            meetingService.findBetweenDates dateStart, dateEnd, user.id
        } else {
            meetingService.findByRoomAndStartTimeAndEndTime date, room, startTime, endTime, pageable
        }
    }


}
