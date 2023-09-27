package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoSubcategory.SubCategoryRequest;
import peaksoft.dto.dtoSubcategory.SubCategoryResponse;
import peaksoft.entity.Category;
import peaksoft.entity.SubCategory;
import peaksoft.exceptions.NoSuchElementException;
import peaksoft.repository.CategoryRepository;
import peaksoft.repository.SubCategoryRepository;
import peaksoft.service.SubCategoryService;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class SubCategoryServiceImpl implements SubCategoryService {
    private final SubCategoryRepository repository;
    private final CategoryRepository categoryRepository;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<SubCategoryResponse> getAllSubCategory() {
        String sql = "SELECT id, name FROM subcategories ;";
        RowMapper<SubCategoryResponse> rowMapper = (rs, rowNum) -> SubCategoryResponse.builder()
                .name(rs.getString("name"))
                .build();

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
        List<SubCategoryResponse> subCategories = namedParameterJdbcTemplate.query(sql, rowMapper);

        return subCategories;
    }

    @Override
    public SimpleResponse saveSubCategory(Long categoryId, SubCategoryRequest subCategoryRequest) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new NoSuchElementException(String.format("Category with id:%s is not found", categoryId)));
        SubCategory subCategory = new SubCategory();
        subCategory.setName(subCategoryRequest.name());
        subCategory.setCategory(category);
        repository.save(subCategory);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("SubCategory with id:%s successfully saved...",subCategory.getId()))
                .build();
    }

    @Override
    public SimpleResponse updateSubCategory(Long id, SubCategoryRequest subCategoryRequest) {
        SubCategory subCategory = repository.findById(id).orElseThrow(() -> new NoSuchElementException(String.format("SubCategory with id:%s not found", id)));
        subCategory.setName(subCategoryRequest.name());
        repository.save(subCategory);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully updated...")
                .build();
    }

    @Override
    public SubCategoryResponse getSubCategoryById(Long id) {
        String sql = "SELECT s.id, s.name, c.name AS categoryName " +
                "FROM subcategories s " +
                "LEFT JOIN categories c ON c.id = s.category_id " +
                "WHERE s.id = ?";

        SubCategoryResponse subCategoryResponse = jdbcTemplate.queryForObject(
                sql,
                new Object[]{id},
                (rs, rowNum) -> SubCategoryResponse.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .categoryName(rs.getString("categoryName"))
                        .build()
        );

        if (subCategoryResponse == null) {
            throw new NoSuchElementException(String.format("SubCategory with id:%s not found", id));
        }

        return subCategoryResponse;
    }



    @Override
    public SimpleResponse deleteSubCategory(Long id) {
        if (repository.existsById(id)){
            repository.deleteById(id);
        }else throw new NoSuchElementException(String.format("SubCategory with id:%s not found", id));
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully deleted...")
                .build();
    }
    @Override
    public List<SubCategoryResponse> searchByName(String word) {
        String sql = "SELECT s.id, s.name, c.name AS categoryName " +
                "FROM subcategories s " +
                "JOIN categories c ON c.id = s.category_id " +
                "WHERE s.name ILIKE :word";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("word", "%" + word + "%");

        RowMapper<SubCategoryResponse> rowMapper = (rs, rowNum) -> SubCategoryResponse.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .categoryName(rs.getString("categoryName"))
                .build();

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
        List<SubCategoryResponse> subCategories = namedParameterJdbcTemplate.query(sql, params, rowMapper);

        return subCategories;
    }
    @Override
    public List<SubCategoryResponse> filterSubCategoryByCategory(String categoryName) {
        String sql = "SELECT s.id, s.name, c.name AS categoryName " +
                "FROM subcategories s " +
                "JOIN categories c ON c.id = s.category_id " +
                "WHERE c.name = ?";

        List<SubCategoryResponse> subCategories = jdbcTemplate.query(
                sql,
                new Object[]{categoryName},
                (rs, rowNum) -> SubCategoryResponse.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .categoryName(rs.getString("categoryName"))
                        .build()
        );

        return subCategories;
    }
    @Override
    public List<SubCategoryResponse> getAllSubCategoryByGroup() {
        String sql = "SELECT s.id, s.name, c.name AS categoryName\n" +
                "FROM subcategories s\n" +
                "         JOIN categories c ON c.id = s.category_id\n" +
                "order by c.name;\n";

        List<SubCategoryResponse> subCategories = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> SubCategoryResponse.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .categoryName(rs.getString("categoryName"))
                        .build()
        );

        return subCategories;
    }


}
