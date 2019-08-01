package com.itechart.meetingcalendar.model.department.service

import com.itechart.meetingcalendar.model.base.BaseService
import com.itechart.meetingcalendar.model.department.entity.Department
import com.itechart.meetingcalendar.model.department.reporsitory.DepartmentRepository
import org.springframework.stereotype.Service

@Service
class DepartmentService extends BaseService<Department> {

    private DepartmentRepository repository

    DepartmentService(DepartmentRepository repository) {
        super(repository)
        this.repository = repository
    }
}
