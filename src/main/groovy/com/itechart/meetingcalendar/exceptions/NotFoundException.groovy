package com.itechart.meetingcalendar.exceptions

class NotFoundException extends CustomResponseException {

    NotFoundException(String message) {
        super(message, 404)
    }
}
