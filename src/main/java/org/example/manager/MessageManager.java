package org.example.manager;

import lombok.NoArgsConstructor;
import org.example.dto.*;
import org.example.model.Person;
import org.example.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Component
@Service
public class MessageManager {
    private NamedParameterJdbcTemplate template;

    @Autowired
    public MessageManager(NamedParameterJdbcTemplate template) {
        this.template = template;
    }


    public List<MessageGetAllResponseDTO> getAll(int limit, int offset) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Person person = personDetails.getPerson();

        return template.query(
                //language=PostgreSQL
                """
                         SELECT u.id, username from users u
                         JOIN message m on u.id = m.user_add or m.user_id != :id
                         JOIN message m2 on u.id = m2.user_id or m2.user_add != :id
                         WHERE m.user_id = :id  OR m.user_id != :id  AND m2.user_add = :id 
                         GROUP BY username, u.id
                        LIMIT :limit OFFSET :offset
                        """,
                Map.of("id", person.getId(),
//                        "user_add", person.getId(),
                        "limit", limit,
                        "offset", offset),
                BeanPropertyRowMapper.newInstance(MessageGetAllResponseDTO.class)
        );

    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public MessageCreateResponseDTO createMessage(MessageCreateRequestDTO dto, long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Person person = personDetails.getPerson();
        if (person.getId() == id) {
            throw new RuntimeException("you can't send a message to yourself");
        }
        MessageCreateResponseDTO dto1 = template.queryForObject(
                //language=PostgreSQL
                """
                        INSERT INTO message (user_id, text,  user_add, outgoing)
                        VALUES (:user_id, :text, :user_add, true)
                        RETURNING id, user_id, text, user_add                     
                        """,

                Map.of("user_id", id,
                        "text", dto.getText(),
                        "user_add", person.getId()),
                BeanPropertyRowMapper.newInstance(MessageCreateResponseDTO.class)
        );
        MessageCreateResponseDTO dto2 = template.queryForObject(
                //language=PostgreSQL
                """
                        INSERT INTO message (user_id, text,  user_add, outgoing)
                        VALUES (:user_id, :text, :user_add, false)
                        RETURNING id, user_id, text, user_add                    
                        """,

                Map.of("user_id", id,
                        "text", dto.getText(),
                        "user_add", person.getId()),
                BeanPropertyRowMapper.newInstance(MessageCreateResponseDTO.class)
        );
        return dto1;
    }

    public List<MessageGetByIdResponseDTO> getBiId(long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Person person = personDetails.getPerson();

        return template.query(
                //language=PostgreSQL
                """
                        SELECT m.id, m.user_add, m.text, u.username sender FROM message m
                        JOIN users u on m.user_add = u.id
                        JOIN users u2 on u2.id = m.user_id
                        WHERE user_add = :id AND outgoing = true OR user_id = :id AND outgoing = false
                        ORDER BY created;
                        """,

                Map.of("id", person.getId()
                ),
                BeanPropertyRowMapper.newInstance(MessageGetByIdResponseDTO.class)
        );


    }

    public MessageDeleteRequestDTO delete(long id) {
        return template.queryForObject(
                //language=PostgreSQL
                """
                        DELETE FROM message WHERE id =:id
                        RETURNING id
                        """,

                Map.of("id", id),
                BeanPropertyRowMapper.newInstance(MessageDeleteRequestDTO.class)
        );

    }
}