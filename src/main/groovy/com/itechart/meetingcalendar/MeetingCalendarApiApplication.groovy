package com.itechart.meetingcalendar


import org.springframework.boot.autoconfigure.SpringBootApplication

import static org.springframework.boot.SpringApplication.run

@SpringBootApplication
//@EnableAuthorizationServer
//@EnableResourceServer
class MeetingCalendarApiApplication {

    static void main(String...args) {
        run(MeetingCalendarApiApplication, args)
    }

}
