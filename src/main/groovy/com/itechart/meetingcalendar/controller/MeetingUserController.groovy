package com.itechart.meetingcalendar.controller

import com.itechart.meetingcalendar.exceptions.BadRequestException
import com.itechart.meetingcalendar.exceptions.ForbiddenException
import com.itechart.meetingcalendar.model.meeting.service.MeetingService
import com.itechart.meetingcalendar.model.meetinguser.entity.MeetingUser
import com.itechart.meetingcalendar.model.meetinguser.service.MeetingUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NO_CONTENT

@RestController("/api/meetings")
class MeetingUserController {

    @Autowired
    private MeetingService meetingService

    @Autowired
    private MeetingUserService meetingUserService

    @ResponseStatus(CREATED)
    @PostMapping("/{id}")
    void postMeetingUser(@PathVariable Long id, @RequestBody List<Long> userIds) {
        def meeting = meetingService.findById id
        if (!meeting) {
            throw new BadRequestException("No such meeting")
        }
        def meetingUsers = meetingUserService.findByMeetingId id
        meetingUsers.each { base ->
            userIds.each { userId ->
                if (base.id == userId) {
                    throw new BadRequestException("User with id ${userId} is already assigned")
                }
            }
        }
        userIds.each { meetingUserService.save new MeetingUser(null, id, it) }
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/{id}")
    void deleteAssignment(@PathVariable Long id, @RequestBody Long userId) {
        def baseMeeting = meetingService.findById id
        if (!baseMeeting) {
            throw new BadRequestException("No such meeting")
        }
        if (!baseMeeting.creatorId) { //todo check user for creator
            throw new ForbiddenException("Only meeting creator can delete assigned users")
        }
        def meetingUser = meetingUserService.findByUserId userId
        if (!meetingUser) {
            throw new BadRequestException("This user is not assigned to meeting")
        }
        meetingUserService.update meetingUser
    }

}
