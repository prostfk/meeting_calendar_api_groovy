package com.itechart.meetingcalendar.model.department.reporsitory

import com.itechart.meetingcalendar.model.department.entity.Department
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface DepartmentRepository extends PagingAndSortingRepository<Department, Long> {

}