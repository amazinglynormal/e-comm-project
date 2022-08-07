package sb.ecomm.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import sb.ecomm.category.Category;
import sb.ecomm.category.CategoryRepository;
import sb.ecomm.exceptions.ProductNotFoundException;
import sb.ecomm.product.dto.CreateProductDTO;
import sb.ecomm.product.dto.ProductDTO;
import sb.ecomm.product.dto.QueryResults;
import sb.ecomm.product.dto.UpdateProductDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    private final ModelMapper mapper = new ModelMapper();

    private ProductService productService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductService(productRepository, categoryRepository, mapper);
    }

    @Test
    void findProductsByCategoriesAndColorsAndSizes() {
        Category cat1 = getTestCategory(1L);
        when(categoryRepository.findByName("test category 1")).thenReturn(Optional.of(cat1));

        Product prod1 = getTestProduct(1L, Color.BLACK, "S", 1L);
        Product prod2 = getTestProduct(2L, Color.BLUE, "S", 1L);

        PageRequest pageRequest = PageRequest.of(0, 10);

        List<Product> products = List.of(prod1, prod2);
        Page<Product> results = new PageImpl<>(products, pageRequest, 2);
        when(productRepository.findProductsByCategoryInAndColorInAndSizeIn(anyList(), anyList(), anyList(), any(Pageable.class))).thenReturn(results);

        List<String> colors = List.of("BLACK", "BLUE");
        List<String> sizes = List.of("S");

        QueryResults queryResults = productService.findProductsByCategoriesAndColorsAndSizes(List.of("test category 1"), colors, sizes, 1);

        assertNotNull(queryResults);
        assertEquals(2, queryResults.getTotalProducts());
        assertEquals(1, queryResults.getTotalPages());
        assertEquals(1L,queryResults.getProducts().get(0).getId());
        assertEquals(2L,queryResults.getProducts().get(1).getId());


    }

    @Test
    void findProductsByCategories() {
        Category cat1 = getTestCategory(1L);
        when(categoryRepository.findByName("test category 1")).thenReturn(Optional.of(cat1));

        Product prod1 = getTestProduct(1L, Color.BLACK, "S", 1L);
        Product prod2 = getTestProduct(2L, Color.BLUE, "S", 1L);

        PageRequest pageRequest = PageRequest.of(0, 10);

        List<Product> products = List.of(prod1, prod2);
        Page<Product> results = new PageImpl<>(products, pageRequest, 2);
        when(productRepository.findProductsByCategoryIn(anyCollection(), any(Pageable.class))).thenReturn(results);

        QueryResults queryResults = productService.findProductsByCategories(List.of("test category 1"), 0);

        assertNotNull(queryResults);
        assertEquals(2, queryResults.getTotalProducts());
        assertEquals(1, queryResults.getTotalPages());
        assertEquals(1L,queryResults.getProducts().get(0).getId());
        assertEquals(2L,queryResults.getProducts().get(1).getId());
    }

    @Test
    void findProductsByCategoriesAndColors() {
        Category cat1 = getTestCategory(1L);
        when(categoryRepository.findByName("test category 1")).thenReturn(Optional.of(cat1));

        Product prod1 = getTestProduct(1L, Color.BLACK, "S", 1L);
        Product prod2 = getTestProduct(2L, Color.BLUE, "S", 1L);

        PageRequest pageRequest = PageRequest.of(0, 10);

        List<Product> products = List.of(prod1, prod2);
        Page<Product> results = new PageImpl<>(products, pageRequest, 2);
        when(productRepository.findProductsByCategoryInAndColorIn(anyList(), anyList(), any(Pageable.class))).thenReturn(results);

        List<String> colors = List.of("BLACK", "BLUE");

        QueryResults queryResults = productService.findProductsByCategoriesAndColors(List.of("test category 1"), colors, 0);

        assertNotNull(queryResults);
        assertEquals(2, queryResults.getTotalProducts());
        assertEquals(1, queryResults.getTotalPages());
        assertEquals(1L,queryResults.getProducts().get(0).getId());
        assertEquals(2L,queryResults.getProducts().get(1).getId());
    }

    @Test
    void findProductsByCategoriesAndSizes() {
        Category cat1 = getTestCategory(1L);
        when(categoryRepository.findByName("test category 1")).thenReturn(Optional.of(cat1));

        Product prod1 = getTestProduct(1L, Color.BLACK, "S", 1L);
        Product prod2 = getTestProduct(2L, Color.BLUE, "S", 1L);

        PageRequest pageRequest = PageRequest.of(0, 10);

        List<Product> products = List.of(prod1, prod2);
        Page<Product> results = new PageImpl<>(products, pageRequest, 2);
        when(productRepository.findProductsByCategoryInAndSizeIn(anyList(), anyList(), any(Pageable.class))).thenReturn(results);

        List<String> sizes = List.of("S");

        QueryResults queryResults = productService.findProductsByCategoriesAndSizes(List.of("test category 1"), sizes, 0);

        assertNotNull(queryResults);
        assertEquals(2, queryResults.getTotalProducts());
        assertEquals(1, queryResults.getTotalPages());
        assertEquals(1L,queryResults.getProducts().get(0).getId());
        assertEquals(2L,queryResults.getProducts().get(1).getId());
    }

    @Test
    void findAlternativeSizesForProduct() {
        Product prod1 = getTestProduct(1L, Color.BLACK, "S", 99L);
        Product prod2 = getTestProduct(2L, Color.BLACK, "M", 99L);
        Product prod3 = getTestProduct(3L, Color.BLACK, "L", 99L);
        Product prod4 = getTestProduct(4L, Color.BLACK, "XL", 99L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(prod1));

        PageRequest pageRequest = PageRequest.of(0, 10);
        List<Product> products = List.of(prod1, prod2, prod3, prod4);
        Page<Product> results = new PageImpl<>(products, pageRequest, 4);

        when(productRepository.findProductsByNameAndCategoryAndColor(eq(prod1.getName()), eq(prod1.getCategory()), eq(prod1.getColor()), any(Pageable.class))).thenReturn(results);

        Iterable<ProductDTO> response = productService.findAlternativeSizesForProduct(1L);

        assertNotNull(response);
        List<ProductDTO> listProdDTOs = new ArrayList<>();
        response.forEach(listProdDTOs::add);
        assertEquals(4, listProdDTOs.size());
        assertEquals(2L, listProdDTOs.get(1).getId());
        assertEquals("XL", listProdDTOs.get(3).getSize());
    }

    @Test
    void findProductById_success() {
        Product product = getTestProduct(1L, Color.BLACK, "S", 99L);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductDTO response = productService.findProductById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("test product", response.getName());
        assertEquals(Color.BLACK, response.getColor());

    }

    @Test
    void findProductByIdThrowsExceptionWhenProductNotFound() {
        when(productRepository.findById(1L)).thenThrow(new ProductNotFoundException(1L));

        assertThrows(ProductNotFoundException.class, () -> productService.findProductById(1L), "Could not find product 1L");
    }


    @Test
    void addNewProduct_success() {
        CreateProductDTO createProductDTO = new CreateProductDTO();
        createProductDTO.setName("test product");
        createProductDTO.setDescription("test description");
        createProductDTO.setColor(Color.BLACK);
        createProductDTO.setSize("S");
        createProductDTO.setCategoryId(99L);

        Product product = getTestProduct(1L, Color.BLACK, "S", 99L);

        when(productRepository.save(any(Product.class))).thenReturn(product);

        when(categoryRepository.findById(99L)).thenReturn(Optional.of(product.getCategory()));

        ProductDTO response = productService.addNewProduct(createProductDTO);

        assertNotNull(response);
        assertEquals("test product", response.getName());
        assertEquals(Color.BLACK, response.getColor());
        assertEquals(99L, response.getCategoryId());

    }

    @Test
    void updateProduct() {
        UpdateProductDTO updateProductDTO = new UpdateProductDTO();
        updateProductDTO.setName("updated name");
        updateProductDTO.setColor(Color.BLUE);
        updateProductDTO.setSize("M");

        Product product = getTestProduct(1L, Color.BLACK, "S", 99L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        product.setName("updated name");
        product.setColor(Color.BLUE);
        product.setSize("M");

        when(productRepository.save(product)).thenReturn(product);

        ProductDTO response = productService.updateProduct(1L, updateProductDTO);

        assertNotNull(response);
        assertEquals("updated name", response.getName());
        assertEquals("M", response.getSize());
        assertEquals(Color.BLUE, response.getColor());
    }

    private Product getTestProduct(Long id, Color color, String size, Long categoryId) {
        Category category = new Category();
        category.setId(categoryId);
        category.setName("test category");
        category.setDescription("test category description");

        Product product = new Product("test product",
                "test description",
                9.99,
                9.99,
                9.99,
                "imageSrc",
                "imageAlt",
                color,
                size,
                true,
                10,
                category);
        product.setId(id);

        return product;
    }

    private Category getTestCategory(Long id) {
        Category category = new Category("test category " + id, "test description", Set.of());
        category.setId(id);
        return category;
    }
}