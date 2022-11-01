package org.example.manager;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.dto.AdsGetAllResponseDTO;
import org.example.dto.CategoriesGetAllResponseDTO;
import org.example.dto.CategoriesGetByIdResponseDTO;
import org.example.dto.UserGetByIdResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

//@NoArgsConstructor
@Component
//@Service
public class CategoriesManager {
    @Autowired
    private NamedParameterJdbcTemplate template;

    public CategoriesManager(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    public List<CategoriesGetAllResponseDTO> getAll() {
        return template.query(
                //language=PostgreSQL
                """
                 SELECT * FROM categories
                 ORDER BY id
                 """,
                Map.of(),
                BeanPropertyRowMapper.newInstance(CategoriesGetAllResponseDTO.class)
        );
    }
    public List<CategoriesGetByIdResponseDTO> getByIdCategories(long id) {
        return template.query(
                // language=PostgreSQL
                """
                        SELECT ads.id, ads.removed, ads.title, ads.description, ads.price, ads.created, c.name FROM ads
                        JOIN categories c on c.id = ads.categories_id
                        WHERE c.id = :id AND ads.removed = false
                        ORDER BY created
                            """,
                Map.of("id", id),
                BeanPropertyRowMapper.newInstance(CategoriesGetByIdResponseDTO.class)
        );

    }
}
