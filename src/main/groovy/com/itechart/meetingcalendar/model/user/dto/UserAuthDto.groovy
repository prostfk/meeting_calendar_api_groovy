package com.itechart.meetingcalendar.model.user.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class UserAuthDto {

    @NotNull
    String email
    @NotNull
    String password

}
