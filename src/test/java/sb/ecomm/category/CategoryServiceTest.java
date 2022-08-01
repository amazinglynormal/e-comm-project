package sb.ecomm.category;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import sb.ecomm.category.dto.CategoryDTO;
import sb.ecomm.category.dto.CreateCategoryDTO;
import sb.ecomm.product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void findAllCategories_success() {
        Category testCategory1 = getTestCategory(1L);
        Category testCategory2 = getTestCategory(2L);
        Iterable<Category> categories = List.of(testCategory1, testCategory2);
        when(categoryRepository.findAll()).thenReturn(categories);

        CategoryDTO categoryDTO1 = getTestCategoryDTO(testCategory1);
        CategoryDTO categoryDTO2 = getTestCategoryDTO(testCategory2);


        when(mapper.map(testCategory1, CategoryDTO.class)).thenReturn(categoryDTO1);
        when(mapper.map(testCategory2, CategoryDTO.class)).thenReturn(categoryDTO2);

        Iterable<CategoryDTO> result = categoryService.findAllCategories();

        List<CategoryDTO> resultList = new ArrayList<>();

        result.forEach(categoryDTO -> resultList.add(categoryDTO));

        assertEquals(2, resultList.size());

        assertEquals(1L, resultList.get(0).getId());
        assertEquals(2L, resultList.get(1).getId());
    }

    @Test
    void addNewCategory() {
        CreateCategoryDTO createCategoryDTO = new CreateCategoryDTO();
        createCategoryDTO.setName("test category");
        createCategoryDTO.setDescription("test category description");

        Category category = getTestCategory(1L);

        when(mapper.map(createCategoryDTO, Category.class)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);

        CategoryDTO categoryDTO = getTestCategoryDTO(category);

        when(mapper.map(category, CategoryDTO.class)).thenReturn(categoryDTO);

        CategoryDTO response = categoryService.addNewCategory(createCategoryDTO);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("test category", response.getName());
    }

    @Test
    void findCategoryById() {
    }

    @Test
    void updateCategory() {
    }

    @Test
    void deleteCategoryById() {
    }

    private Category getTestCategory(Long id) {
        Category category = new Category();
        category.setName("test category");
        category.setDescription("test category description");
        category.setId(id);
        Product product = getTestProduct();
        category.setProducts(Set.of(product));

        return category;
    }

    private CategoryDTO getTestCategoryDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName(category.getName());
        categoryDTO.setDescription(category.getDescription());
        categoryDTO.setProducts(category.getProducts());
        categoryDTO.setId(category.getId());

        return categoryDTO;
    }

    private Product getTestProduct() {
        Product product = new Product();
        product.setName("test-product");
        product.setDescription("test-description");
        product.setEUR(9.99);
        product.setStockRemaining(10);
        product.setId(1L);

        return product;
    }
}