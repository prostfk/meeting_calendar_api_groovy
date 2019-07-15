package com.itechart.meetingcalendar.model.user.repository

import com.itechart.meetingcalendar.model.user.entity.User
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository extends PagingAndSortingRepository<User, Long> {

    User findByUsername(String username)
}