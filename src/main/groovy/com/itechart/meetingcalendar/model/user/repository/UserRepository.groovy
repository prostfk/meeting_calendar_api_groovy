package com.itechart.meetingcalendar.model.user.repository

import com.itechart.meetingcalendar.model.user.dto.IUserDto
import com.itechart.meetingcalendar.model.user.dto.UserProfileDto
import com.itechart.meetingcalendar.model.user.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserRepository extends PagingAndSortingRepository<User, Long> {

    User findByUsername(String username)

    @Query(value = "SELECT u.id, u.first_name, u.last_name FROM users u WHERE CONCAT(u.first_name, ' ', last_name) LIKE :fullName", nativeQuery = true)
    Page<IUserDto> findByFullName(@Param("fullName") String fullName, Pageable pageable)

    User findByUsernameOrEmail(String username, String email)

    User findUserByEmailAndActive(String email, boolean active)

    User findByEmail(String email)

    @Query(value = "SELECT first_name as firstName, last_name as lastName, gender, patronymic, room, username, email, department FROM users WHERE username=:username", nativeQuery=true)
    UserProfileDto findUserProfileInfoUsername(@Param("username") String username)
}