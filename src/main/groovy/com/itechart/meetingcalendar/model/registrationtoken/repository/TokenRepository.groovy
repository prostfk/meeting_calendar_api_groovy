package com.itechart.meetingcalendar.model.registrationtoken.repository

import com.itechart.meetingcalendar.model.registrationtoken.entity.Token
import com.itechart.meetingcalendar.model.registrationtoken.entity.TokenType
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface TokenRepository extends PagingAndSortingRepository<Token, Long> {

    Token findByValue(String token)

    Token findByValueAndType(String value, TokenType type)
}