package org.example.manager;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.dto.AdsGetAllResponseDTO;
import org.example.dto.CategoriesGetAllResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
}
