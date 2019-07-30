package com.itechart.meetingcalendar.exceptions

class CustomBodyResponseException extends Exception {

    String key
    int code

    CustomBodyResponseException(String key, String message, int code) {
        super(message)
        this.key = key
        this.code = code
    }
}
