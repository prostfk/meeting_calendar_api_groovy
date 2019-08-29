package com.itechart.meetingcalendar.model.note.repository

import com.itechart.meetingcalendar.model.note.entity.Note
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface NoteRepository extends PagingAndSortingRepository<Note, Long> {
    Page<Note> findByUserId(Long userId, Pageable pageable)
    Page<Note> findByUserIdAndActive(Long userId, Pageable pageable, boolean active)

}