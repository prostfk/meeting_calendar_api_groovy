package com.itechart.meetingcalendar.model.user.service

import com.itechart.meetingcalendar.exceptions.BadRequestException
import com.itechart.meetingcalendar.exceptions.CustomBodyResponseException
import com.itechart.meetingcalendar.model.base.BaseService
import com.itechart.meetingcalendar.model.user.dto.FindUserDto
import com.itechart.meetingcalendar.model.user.dto.IUserDto
import com.itechart.meetingcalendar.model.user.dto.UserDto
import com.itechart.meetingcalendar.model.user.dto.UserProfileDto
import com.itechart.meetingcalendar.model.user.entity.User
import com.itechart.meetingcalendar.model.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

import java.sql.ResultSet
import java.sql.SQLException

@Service
class UserService extends BaseService<User> {

    private UserRepository repository
    private PasswordEncoder passwordEncoder
    private JdbcTemplate jdbcTemplate

    @Autowired
    UserService(UserRepository repository, PasswordEncoder passwordEncoder, JdbcTemplate jdbcTemplate) {
        super(repository)
        this.repository = repository
        this.passwordEncoder = passwordEncoder
        this.jdbcTemplate = jdbcTemplate
    }

    User findByUsername(String username) {
        repository.findByUsername username
    }

    Page<IUserDto> findByFullName(String fullName, Pageable pageable) {
        repository.findByFullName fullName, pageable
    }

    User findByUsernameOrEmail(String usernameOrEmail) {
        repository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
    }

    User registerUser(User user) {
        def byEmail = findByEmail user.email
        if (byEmail) {
            throw new CustomBodyResponseException("email", "User with this email already exists", 400)
        }

        def byUsername = findByUsername user.username
        if (byUsername) {
            throw new CustomBodyResponseException("username", "User with this username already exists", 400)
        }
        if (user.password.length() < 6 || user.password.length() > 20) {
            throw new CustomBodyResponseException("password", "Password must be between 6 and 20 chars", 400)
        }
        user.id = null
        user.password = passwordEncoder.encode(user.password)
        save(user)
    }

    User findByEmail(String email) {
        repository.findByEmail email
    }

    UserProfileDto findUserProfileInfo(String username) {
        repository.findUserProfileInfoUsername(username)
    }

    Page<UserDto> findUsers(FindUserDto params, Pageable pageable) {
        if (params == null) {
            throw new BadRequestException("Provide params")
        }
        def base = new StringBuilder("SELECT id, first_name as firstName, last_name as lastName, room, department, email, avatar FROM users ")
        def needWhere = true
        if (params.room) {
            append(needWhere, base, " room = ?")
            needWhere = false
        }
        if (params.department) {
            append(needWhere, base, "department = ?")
            needWhere = false
        }
        if (params.query) {
            append(needWhere, base, "CONCAT(first_name, ' ', last_name) LIKE ?")
        }

        base.append(" LIMIT ").append(pageable.pageSize).append(" OFFSET ").append(pageable.pageNumber * pageable.pageSize)
        def query = jdbcTemplate.query(base.toString(), buildSearchParams(params), new RowMapper<UserDto>() {
            @Override
            UserDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                new UserDto(
                        rs.getLong("id"), rs.getString("firstName"), rs.getString("lastName"),
                        rs.getString("department"), rs.getString("email"), rs.getString("avatar"),
                        rs.getInt("room")
                )
            }
        })
        new PageImpl<UserDto>(query)
    }

    private static Object[] buildSearchParams(FindUserDto params) {
        def parameters = []
        if (params.room) {
            parameters.add(params.room)
        }
        if (params.department) {
            parameters.add(params.department)
        }
        if (params.query) {
            parameters.add("%${params.query}%")
        }
        return parameters.toArray()
    }

    private static void append(boolean needWhere, StringBuilder sb, String sql) {
        if (needWhere) {
            sb.append(" WHERE ").append(sql)
        } else {
            sb.append(" AND ").append(sql)
        }
    }

}
