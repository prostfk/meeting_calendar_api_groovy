package com.itechart.meetingcalendar.model.meeting.service

import com.itechart.meetingcalendar.exceptions.BadRequestException
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

    Page<Meeting> findByRoomAndStartTimeAndEndTime(Date date, Integer room, String startTime, String endTime, Pageable pageable) {
        if (!room) {
            throw new BadRequestException("Room is required")
        } else if (!startTime) {
            throw new BadRequestException("Start time is required")
        } else if (!endTime) {
            throw new BadRequestException("End time is required")
        } else if (!pageable) {
            throw new BadRequestException("Page details are required")
        }
        repository.findByRoomAndStartTimeAndEndTime date, room, "${startTime}:20.08321", "${endTime}:20.08321", pageable
    }

    List<Meeting> findBetweenDates(Date dateStart, Date dateEnd, Long userId) {
        repository.findBetweenDates dateStart, dateEnd, userId
    }
}
