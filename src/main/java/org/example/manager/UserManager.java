package org.example.manager;

import lombok.NoArgsConstructor;
import org.example.dto.UserGetByIdResponseDTO;
import org.example.dto.UserMeResponseDTO;
import org.example.dto.UserRegisterRequestDTO;
import org.example.model.Person;
import org.example.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Component
public class UserManager {
    private NamedParameterJdbcTemplate template;

    @Autowired
    public UserManager(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public Person register(UserRegisterRequestDTO dto) {
        String encodePassword = passwordEncoder.encode(dto.getPassword());
        Person person = new Person();
        String role = person.getRole();
        if (dto.getPassword().equals("admin")) {
            role = "ROLE_ADMIN";
        } else {
            role = "ROLE_USER";
        }
        return template.queryForObject(
                // language=PostgreSQL
                """
                            INSERT INTO users(username, password, role) 
                            VALUES (:username, :password, :role)
                            RETURNING id, username, role
                        """,
                Map.of("username", dto.getUsername(),
                        "password", encodePassword,
                        "role", role),
//                new Object[]{dto.getUsername(), encodePassword, role},
                BeanPropertyRowMapper.newInstance(Person.class)
        );
    }

    public List<UserGetByIdResponseDTO> getByIdUserAds(long id) {
        return template.query(
                // language=PostgreSQL
                """
                        SELECT ads.id, ads.title, ads.description, ads.price, ads.created, categories.name, users.id, users.username FROM ads
                        JOIN categories ON ads.categories_id = categories.id
                        JOIN users ON ads.user_id = users.id
                        WHERE user_id = :user_id
                            """,
                Map.of("user_id", id),
//                new Object[]{id},
                BeanPropertyRowMapper.newInstance(UserGetByIdResponseDTO.class)
        );

    }

    // me.html
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<UserMeResponseDTO> me() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Person person = personDetails.getPerson();
        return template.query(
                // language=PostgreSQL
                """
                        SELECT ads.id, ads.removed, ads.title, ads.description, ads.price, ads.created, categories.name  FROM ads
                        JOIN categories ON ads.categories_id = categories.id
                        JOIN users u ON ads.user_id = u.id
                        WHERE user_id = :user_id AND ads.removed = false AND u.removed = false 
                            """,
                Map.of("user_id", person.getId()),
//                new Object[]{person.getId()},
                BeanPropertyRowMapper.newInstance(UserMeResponseDTO.class)
        );
    }

    public List<UserMeResponseDTO> restoreList() {
        return template.query(
                // language=PostgreSQL
                """
                        SELECT ads.id, ads.removed, ads.title, ads.description, ads.price, ads.created, categories.name  FROM ads
                        JOIN categories ON ads.categories_id = categories.id
                        JOIN users ON ads.user_id = users.id
                        WHERE  ads.removed = true
                            """,
                Map.of(),
//                new Object[]{},
                BeanPropertyRowMapper.newInstance(UserMeResponseDTO.class)
        );
    }

    public UserGetByIdResponseDTO userGetById(long id) {
        return template.queryForObject(
                //language=PostgreSQL
                """
                        SELECT id, username FROM users
                        WHERE id = :id AND removed = false
                        """,
                Map.of("id", id),
//                new Object[]{id},
                BeanPropertyRowMapper.newInstance(UserGetByIdResponseDTO.class)
        );
    }
}
