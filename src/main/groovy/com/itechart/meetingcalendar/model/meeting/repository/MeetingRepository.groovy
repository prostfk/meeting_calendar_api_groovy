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

    @Query(value = "SELECT DISTINCT m.* FROM meeting m JOIN meeting_user mu ON m.id = mu.meeting_id WHERE date BETWEEN :startDate AND :endDate AND (m.creator_id = :userId OR mu.user_id = :userId)", nativeQuery = true)
    List<Meeting> findBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("userId") Long userId)

    @Query(value = "SELECT DISTINCT m.* FROM meeting m JOIN meeting_user mu ON m.id = mu.meeting_id WHERE creator_id = :userId OR mu.user_id = :userId GROUP BY m.id ", nativeQuery = true)
    Page<Meeting> findAllowedMeetings(@Param("userId") Long userId, Pageable pageable)

    @Query(value = """SELECT *
                        FROM meeting
                        WHERE date = :date
                          AND room = :room
                          AND ((CAST(:st as TIME) > CAST(CONCAT(start_time, ':20.0343') as TIME) AND CAST(CONCAT(start_time, ':20.0343') as TIME) < CAST(:et as TIME))
                           AND (CAST(:st as TIME) > CAST(CONCAT(end_time, ':20.0343') as TIME) AND CAST(CONCAT(end_time, ':20.0343') as TIME) < CAST(:et as TIME)))""", nativeQuery = true)
    Page<Meeting> findByRoomAndStartTimeAndEndTime(@Param("date") Date date, @Param("room") Integer room, @Param("st") String startTime, @Param("et") String endTime, Pageable pageable)

}