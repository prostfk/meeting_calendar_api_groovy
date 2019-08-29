package com.itechart.meetingcalendar.model.note.service

import com.itechart.meetingcalendar.model.base.BaseService
import com.itechart.meetingcalendar.model.note.entity.Note
import com.itechart.meetingcalendar.model.note.repository.NoteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Service

@Service
class NoteService extends BaseService<Note> {

    private NoteRepository repository

    @Autowired
    NoteService(NoteRepository repository) {
        super(repository)
        this.repository = repository
    }

    Page<Note> findByUserIdAndActive(Long id, Pageable pageable, boolean active) {
        repository.findByUserIdAndActive id, pageable, active
    }

}
