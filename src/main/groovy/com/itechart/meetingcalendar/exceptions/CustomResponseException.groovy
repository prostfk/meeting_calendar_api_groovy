package com.itechart.meetingcalendar.exceptions

class CustomResponseException extends Exception {

    int code

    CustomResponseException(String message, int code) {
        super(message)
        this.code = code
    }
}
