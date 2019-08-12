package com.itechart.meetingcalendar.controller.advice

import com.itechart.meetingcalendar.exceptions.CustomBodyResponseException
import com.itechart.meetingcalendar.exceptions.CustomResponseException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ControllerExceptionHandler {

    @ExceptionHandler
    ResponseEntity customResponse(CustomResponseException e) {
        new ResponseEntity("{\"error\": \"${e.getMessage()}\"}", HttpStatus.valueOf(e.code))
    }

    @ExceptionHandler
    ResponseEntity customBodyResponse(CustomBodyResponseException e) {
        new ResponseEntity("{\"${e.key}\": \"${e.message}\"}", HttpStatus.valueOf(e.code))
    }

}
