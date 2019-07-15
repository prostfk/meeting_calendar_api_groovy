package com.itechart.meetingcalendar.model.meetinguser.repository

import com.itechart.meetingcalendar.model.meetinguser.entity.MeetingUser
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface MeetingUserRepository extends PagingAndSortingRepository<MeetingUser, Long> {

    List<MeetingUser> findByMeetingId(Long meetingId)

    MeetingUser findByUserId(Long userId)
}