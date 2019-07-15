package com.itechart.meetingcalendar.model.meetinguser.service

import com.itechart.meetingcalendar.model.base.BaseService
import com.itechart.meetingcalendar.model.meetinguser.entity.MeetingUser
import com.itechart.meetingcalendar.model.meetinguser.repository.MeetingUserRepository
import org.springframework.stereotype.Service

@Service
class MeetingUserService extends BaseService<MeetingUser> {

    private MeetingUserRepository repository

    MeetingUserService(MeetingUserRepository repository) {
        super(repository)
        this.repository = repository
    }

    List<MeetingUser> findByMeetingId(Long meetingId) {
        repository.findByMeetingId meetingId
    }

    MeetingUser findByUserId(Long id) {
        repository.findByUserId id
    }
}
