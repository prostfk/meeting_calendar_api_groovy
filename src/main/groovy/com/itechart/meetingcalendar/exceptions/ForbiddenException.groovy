package com.itechart.meetingcalendar.exceptions

class ForbiddenException extends CustomResponseException {

    ForbiddenException(String message) {
        super(message, 401)
    }

}
