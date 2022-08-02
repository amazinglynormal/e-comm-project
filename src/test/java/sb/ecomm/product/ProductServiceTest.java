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
import sb.ecomm.product.dto.CreateProductDTO;
import sb.ecomm.product.dto.ProductDTO;
import sb.ecomm.product.dto.QueryResults;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        Product product = getTestProduct(1L, Color.BLACK, "S", 99L);
        ProductDTO productDTO = getTestProductDTO(product);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(mapper.map(product, ProductDTO.class)).thenReturn(productDTO);

        ProductDTO response = productService.findProductById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("test product", response.getName());
        assertEquals(Color.BLACK, response.getColor());

    }

    @Test
    void findProductByIdThrowsExceptionWhenProductNotFound() {
        when(productRepository.findById(1L)).thenThrow(new ProductNotFoundException(1L));

        assertThrows(ProductNotFoundException.class, () -> {
            productService.findProductById(1L);
        }, "Could not find product 1L");
    }



//    private String name;
//
//    private String description;
//
//    private double USD;
//
//    private double EUR;
//
//    private double GBP;
//
//    private String imageSrc;
//
//    private String imageAlt;
//
//    private Color color;
//
//    private String size;
//
//    private boolean inStock;
//
//    private int stockRemaining;
//
//    private Long categoryId;
    @Test
    void addNewProduct_success() {
        CreateProductDTO createProductDTO = new CreateProductDTO();
        createProductDTO.setName("test product");
        createProductDTO.setDescription("test description");
        createProductDTO.setColor(Color.BLACK);
        createProductDTO.setSize("S");
        createProductDTO.setCategoryId(99L);

        Product product = getTestProduct(1L, Color.BLACK, "S", 99L);

        when(mapper.map(createProductDTO, Product.class)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);

        when(categoryRepository.findById(99L)).thenReturn(Optional.of(product.getCategory()));

        ProductDTO productDTO = getTestProductDTO(product);

        when(mapper.map(product, ProductDTO.class)).thenReturn(productDTO);

        ProductDTO response = productService.addNewProduct(createProductDTO);

        assertNotNull(response);
        assertEquals("test product", response.getName());
        assertEquals(Color.BLACK, response.getColor());
        assertEquals(99L, response.getCategoryId());

    }

    @Test
    void updateProduct() {
    }

    @Test
    void deleteProductById() {
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