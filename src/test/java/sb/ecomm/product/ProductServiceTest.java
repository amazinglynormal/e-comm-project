package sb.ecomm.product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import sb.ecomm.category.Category;
import sb.ecomm.category.CategoryRepository;
import sb.ecomm.exceptions.ProductNotFoundException;
import sb.ecomm.product.dto.ProductDTO;
import sb.ecomm.product.dto.QueryResults;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private ProductService productService;

    @Test
    void findProductsByCategoriesAndColorsAndSizes() {

    }

    @Test
    void findProductsByCategories() {
    }

    @Test
    void findProductsByCategoriesAndColors() {
    }

    @Test
    void findProductsByCategoriesAndSizes() {
    }

    @Test
    void findAlternativeSizesForProduct() {
    }

    @Test
    void findProductById_success() {
        Product product = getTestProduct(1L);
        ProductDTO productDTO = getTestProductDTO(product);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(mapper.map(product, ProductDTO.class)).thenReturn(productDTO);

        ProductDTO response = productService.findProductById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("test product", response.getName());

    }

    @Test
    void findProductByIdThrowsExceptionWhenProductNotFound() {
        when(productRepository.findById(1L)).thenThrow(new ProductNotFoundException(1L));

        assertThrows(ProductNotFoundException.class, () -> {
            productService.findProductById(1L);
        }, "Could not find product 1L");
    }

    @Test
    void addNewProduct() {
    }

    @Test
    void updateProduct() {
    }

    @Test
    void deleteProductById() {
    }

    private Product getTestProduct(Long id) {
        Category category = new Category();
        category.setId(99L);
        category.setName("test category");
        category.setDescription("test category description");

        Product product = new Product("test product",
                "test description",
                9.99,
                9.99,
                9.99,
                "imageSrc",
                "imageAlt",
                Color.BLACK,
                "S",
                true,
                10,
                category);
        product.setId(id);

        return product;
    }

    private ProductDTO getTestProductDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setUSD(product.getUSD());
        productDTO.setGBP(product.getGBP());
        productDTO.setEUR(product.getEUR());
        productDTO.setImageSrc(product.getImageSrc());
        productDTO.setImageAlt(product.getImageAlt());
        productDTO.setColor(product.getColor());
        productDTO.setSize(product.getSize());
        productDTO.setInStock(product.isInStock());
        productDTO.setCategoryId(product.getCategory().getId());

        return productDTO;
    }

    private QueryResults getQueryResults(List<ProductDTO> productDTOList) {
        return new QueryResults(productDTOList, productDTOList.size(), 1);
    }
}