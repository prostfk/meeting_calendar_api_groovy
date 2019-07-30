package com.itechart.meetingcalendar.service


enum EmailTemplate {

    INVITE("Invite.html"),
    REGISTRATION("Registration.html"),
    PASSWORD_RESET("PasswordReset.html");

    String templateName

    private EmailTemplate(String templateName) {
        this.templateName = templateName
    }
}