package com.itechart.meetingcalendar.model.user.dto

import javax.validation.constraints.NotBlank

class UserAuthDto {

    @NotBlank
    String username
    @NotBlank
    String password

}
