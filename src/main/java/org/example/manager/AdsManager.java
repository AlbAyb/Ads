package org.example.manager;

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

import java.util.List;
import java.util.Map;

@Component
public class AdsManager {

    private NamedParameterJdbcTemplate template;

    @Autowired
    public AdsManager(NamedParameterJdbcTemplate template) {
        this.template = template;
    }


    public List<AdsGetAllResponseDTO> getAll(int limit, int offset) {
        return template.query(
                //language=PostgreSQL
                """
                        SELECT ads.id, ads.title, ads.price, categories.name, users.username FROM ads
                        JOIN categories ON ads.categories_id = categories.id 
                        JOIN users ON ads.user_id = users.id 
                        WHERE ads.removed = false AND users.removed = false
                        ORDER BY price
                        LIMIT :limit OFFSET :offset
                                                """,
                Map.of(
                        "limit", limit,
                        "offset", offset
                ),
                BeanPropertyRowMapper.newInstance(AdsGetAllResponseDTO.class)
        );
    }

    public AdsGetByIdResponseDTO getById(long id) {
        return template.queryForObject(
                // language=PostgreSQL
                """
                        SELECT ads.id, ads.title, ads.price, ads.description, ads.user_id, categories.name, users.username 
                        FROM ads
                        JOIN categories on ads.categories_id = categories.id
                        JOIN users ON ads.user_id = users.id
                        WHERE  ads.id = :id
                        ORDER BY id
                                               
                        """,
                Map.of("id", id),
                BeanPropertyRowMapper.newInstance(AdsGetByIdResponseDTO.class)
        );
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    public AdsCreateResponseDTO addAds(AdsCreateRequestDTO create) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Person person = personDetails.getPerson();
        return template.queryForObject(
                // language=PostgreSQL
                """
                        INSERT INTO ads(title, price, description, user_id, categories_id)    
                        VALUES (:title, :price, :description, :user_id, :categories_id) 
                        RETURNING id, title, price, description, user_id, categories_id                      
                         """,
                Map.of("title", create.getTitle(),
                        "price", create.getPrice(),
                        "description", create.getDescription(),
                        "user_id", person.getId(),
                        "categories_id", create.getName()),
                BeanPropertyRowMapper.newInstance(AdsCreateResponseDTO.class)
        );
    }

    public UserRemoveByIdResponseDTO delete(long id) {
        return template.queryForObject(
                //language=PostgreSQL
                """
                        UPDATE ads SET  removed = TRUE WHERE id = :id
                        RETURNING id
                        """,
                Map.of("id", id),
                BeanPropertyRowMapper.newInstance(UserRemoveByIdResponseDTO.class)
        );
    }

    public AdsRestoreRequestDTO restore(long id) {
        return template.queryForObject(
                //language=PostgreSQL
                """
                                UPDATE ads
                                    SET removed = false
                                    WHERE  id = :id
                                    RETURNING id, title, price, description
                        """,
                Map.of("id", id),
                BeanPropertyRowMapper.newInstance(AdsRestoreRequestDTO.class)
        );
    }

    public AdsUpdateResponseDTO update(long id, AdsUpdateRequestDTO dto) {
        return template.queryForObject(
                //language=PostgreSQL
                """
                                UPDATE ads
                                    SET title = :title, price = :price, description = :description
                                    WHERE  id = :id
                                    RETURNING id, title, price, description
                        """,

                Map.of("id", id,
                        "title", dto.getTitle(),
                        "price", dto.getPrice(),
                        "description", dto.getDescription()),
                BeanPropertyRowMapper.newInstance(AdsUpdateResponseDTO.class)
        );
    }


}

