package com.itechart.meetingcalendar.model.meeting.repository

import com.itechart.meetingcalendar.model.meeting.entity.Meeting
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface MeetingRepository extends PagingAndSortingRepository<Meeting, Long> {



}