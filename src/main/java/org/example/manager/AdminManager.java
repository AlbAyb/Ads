package org.example.manager;

import org.example.dto.UserGetAllResponseDTO;
import org.example.dto.UserRemoveByIdResponseDTO;
import org.example.dto.UserRestoreResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Component
//@Service
public class AdminManager {
    private NamedParameterJdbcTemplate template;
    @Autowired
    public AdminManager(NamedParameterJdbcTemplate template) {
        this.template = template;
    }




    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserGetAllResponseDTO> getAll(int limit, int offset) {
        UserGetAllResponseDTO dto = new UserGetAllResponseDTO();

        return template.query(
                //language=PostgreSQL
                """
                        SELECT u.id, u.username, u.role, u.removed, count(ads.id) userId FROM ads
                        RIGHT JOIN users u on u.id = ads.user_id
                        WHERE u.username != 'ADMIN'
                        GROUP BY u.id
                        ORDER BY id
                        LIMIT :limit OFFSET :offset
                        """,
                Map.of(
                        "limit", limit,
                        "offset", offset
                ),
                BeanPropertyRowMapper.newInstance(UserGetAllResponseDTO.class)
        );
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserRemoveByIdResponseDTO delete(long id) {
        return template.queryForObject(
                //language=PostgreSQL
                """
                        UPDATE users SET  removed = TRUE WHERE id = :id
                        RETURNING id
                        """,
                Map.of("id", id),
                BeanPropertyRowMapper.newInstance(UserRemoveByIdResponseDTO.class)
        );
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserRestoreResponseDTO restoreUser(long id) {
        return template.queryForObject(
                //language=PostgreSQL
                """
                        UPDATE users SET removed = FALSE
                        WHERE id = :id
                        RETURNING id
                        """,
                Map.of("id", id),
                BeanPropertyRowMapper.newInstance(UserRestoreResponseDTO.class)
        );
    }
}
