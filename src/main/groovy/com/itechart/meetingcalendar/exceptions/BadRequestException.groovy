package com.itechart.meetingcalendar.exceptions

class BadRequestException extends CustomResponseException {

    BadRequestException(String message) {
        super(message, 400)
    }
}
