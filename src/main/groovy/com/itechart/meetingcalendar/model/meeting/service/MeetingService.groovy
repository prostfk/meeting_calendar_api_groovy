package com.itechart.meetingcalendar.model.meeting.service

import com.itechart.meetingcalendar.model.base.BaseService
import com.itechart.meetingcalendar.model.meeting.entity.Meeting
import com.itechart.meetingcalendar.model.meeting.repository.MeetingRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class MeetingService extends BaseService<Meeting> {

    private MeetingRepository repository

    @Autowired
    MeetingService(MeetingRepository repository) {
        super(repository)
        this.repository = repository
    }

    @Override
    Meeting findById(Long id) {
        repository.findByIdAndActive id, true
    }

    Page<Meeting> findAllowedMeetings(Long userId, Pageable pageable) {
        repository.findAllowedMeetings userId, pageable
    }

}
