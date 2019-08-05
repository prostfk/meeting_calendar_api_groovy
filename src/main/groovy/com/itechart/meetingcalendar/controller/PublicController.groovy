package com.itechart.meetingcalendar.controller

import com.itechart.meetingcalendar.exceptions.NotFoundException
import com.itechart.meetingcalendar.model.department.entity.Department
import com.itechart.meetingcalendar.model.department.service.DepartmentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/public/api")
class PublicController {

    @Autowired
    private DepartmentService departmentService

    @Value('${attachments.path}')
    private String resourcePath

    @GetMapping("/departments")
    Iterable<Department> getDepartments() {
        departmentService.findAll()
    }

    @GetMapping("/avatars/{id}/avatar.png")
    byte[] getAvatars(@PathVariable Long id) {
        def file = new File("${resourcePath}/avatars/${id}/avatar.png")
        if (file.exists()) {
            return file.bytes
        }
        throw new NotFoundException("No such avatar")
    }

}
