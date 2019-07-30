package com.itechart.meetingcalendar.model.meeting.repository

import com.itechart.meetingcalendar.model.meeting.entity.Meeting
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface MeetingRepository extends PagingAndSortingRepository<Meeting, Long> {

    Meeting findByIdAndActive(Long id, Boolean active)

    @Query(value = "SELECT DISTINCT m.* FROM meeting m JOIN meeting_user mu ON m.id = mu.meeting_id WHERE creator_id = :userId OR mu.user_id = :userId GROUP BY m.id ", nativeQuery = true)
    Page<Meeting> findAllowedMeetings(@Param("userId")Long userId, Pageable pageable)
}