package com.itechart.meetingcalendar.model.base

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service

import javax.transaction.Transactional

abstract class BaseService<T extends BaseEntity> {

    private CrudRepository<T, Long> crudRepository

    BaseService(CrudRepository<T, Long> crudRepository) {
        this.crudRepository = crudRepository
    }

    T save(T t) {
        crudRepository.save(t)
    }

    List<T> save(List<T> t) {
        for (T t1 : t) {
            save(t1)
        }
        t
    }

    T update(T t) {
        if (t && t.id) {
            return crudRepository.save(t)
        }
        null
    }

    @Transactional
    List<T> update(List<T> ts) {
        List<T> upd = new ArrayList<>(ts.size())
        ts.forEach({ t -> upd.add(update(t)) })
        upd
    }

    void delete(T t) {
        crudRepository.delete(t)
    }

    T findById(Long id) {
        crudRepository.findById(id).orElse(null)
    }

    Iterable<T> findAll() {
        crudRepository.findAll()
    }

}
