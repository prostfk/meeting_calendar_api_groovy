package com.itechart.meetingcalendar.model.registrationtoken.service

import com.itechart.meetingcalendar.model.base.BaseService
import com.itechart.meetingcalendar.model.registrationtoken.entity.Token
import com.itechart.meetingcalendar.model.registrationtoken.entity.TokenType
import com.itechart.meetingcalendar.model.registrationtoken.repository.TokenRepository
import org.springframework.stereotype.Service

@Service
class TokenService extends BaseService<Token> {

    private TokenRepository repository

    TokenService(TokenRepository repository) {
        super(repository)
        this.repository = repository
    }

    Token findByValue(String token) {
        repository.findByValue token
    }

    @Override
    Token save(Token token) {
        token.value = UUID.randomUUID().toString().replaceAll("-", "")
        super.save(token)
    }

    Token findByValueAndType(String value, TokenType type) {
        repository.findByValueAndType value, type
    }
}
