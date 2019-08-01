package com.itechart.meetingcalendar.controller

import com.itechart.meetingcalendar.model.department.entity.Department
import com.itechart.meetingcalendar.model.department.service.DepartmentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/public/api")
class PublicController {

    @Autowired
    private DepartmentService departmentService

    @GetMapping("/departments")
    Iterable<Department> getDepartments() {
        departmentService.findAll()
    }

}
