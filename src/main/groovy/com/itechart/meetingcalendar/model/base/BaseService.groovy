package com.itechart.meetingcalendar.model.base

import com.itechart.meetingcalendar.exceptions.BadRequestException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository

import javax.transaction.Transactional

abstract class BaseService<T extends BaseEntity> {

    private PagingAndSortingRepository<T, Long> repository

    BaseService(PagingAndSortingRepository<T, Long> repository) {
        this.repository = repository
    }

    T save(T t) {
        repository.save t
    }

    List<T> save(List<T> t) {
        for (T t1 : t) {
            save t1
        }
        t
    }

    T update(T t) {
        if (t) {
            if (!t.id) {
                throw new BadRequestException("No id provided")
            }
            return repository.save(t)
        }
        null
    }

    @Transactional
    List<T> update(List<T> ts) {
        List<T> upd = new ArrayList<>(ts.size())
        ts.forEach({ t -> upd.add update(t) })
        upd
    }

    void delete(T t) {
        if (t instanceof SafeDeleted) {
            def safe = (SafeDeleted) t
            safe.active = false
            update t
        } else {
            repository.delete t
        }
    }

    T findById(Long id) {
        repository.findById(id).orElse(null)
    }

    Iterable<T> findAll() {
        repository.findAll()
    }

    Page<T> findAll(Pageable pageable) {
        repository.findAll pageable
    }

}
