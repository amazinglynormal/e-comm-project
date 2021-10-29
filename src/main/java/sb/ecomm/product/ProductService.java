package sb.ecomm.product;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sb.ecomm.category.Category;
import sb.ecomm.exceptions.CategoryNotFoundException;
import sb.ecomm.category.CategoryRepository;
import sb.ecomm.exceptions.ProductNotFoundException;
import sb.ecomm.product.dto.CreateProductDTO;
import sb.ecomm.product.dto.ProductDTO;
import sb.ecomm.product.dto.UpdateProductDTO;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private ModelMapper mapper;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, ModelMapper mapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    Iterable<ProductDTO> findAllProducts() {
        Iterable<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOs = new ArrayList<>();
        products.forEach(product -> {
            productDTOs.add(mapper.map(product, ProductDTO.class));
        });

        return productDTOs;
    }

    Iterable<ProductDTO> findProductsInCategory(Long categoryId, int page) {
        Category category =
                categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));
        Pageable pageable = PageRequest.of(page, 15);
        Iterable<Product> products =
                productRepository.findProductByCategory(category, pageable);
        List<ProductDTO> productDTOs = new ArrayList<>();
        products.forEach(product -> {
            productDTOs.add(mapper.map(product, ProductDTO.class));
        });

        return productDTOs;
    }

    ProductDTO findProductById(Long id) {
        Product product =
                productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        return mapper.map(product, ProductDTO.class);
    }

    List<ProductDTO> findProductByName(String name) {
        List<Product> products =
                productRepository.findByNameContainingIgnoreCase(name);
        List<ProductDTO> productDTOs = new ArrayList<>();
        products.forEach(product -> {
            productDTOs.add(mapper.map(product, ProductDTO.class));
        });

        return productDTOs;
    }

    ProductDTO addNewProduct(CreateProductDTO newProductDto) {
        System.out.println(newProductDto.toString());
        Product newProduct = mapper.map(newProductDto, Product.class);
        Category category =
                categoryRepository.findById(newProductDto.getCategoryId()).orElseThrow(() -> new CategoryNotFoundException(newProductDto.getCategoryId()));
        newProduct.setCategory(category);
        Product savedProduct = productRepository.save(newProduct);
        return mapper.map(savedProduct, ProductDTO.class);
    }

    ProductDTO updateProduct(Long id, UpdateProductDTO updatedProductDTO) {
        Product product =
                productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        updateProductName(product,updatedProductDTO);
        updateProductDescription(product,updatedProductDTO);
        updateProductFeatures(product,updatedProductDTO);
        updateProductPriceEUR(product,updatedProductDTO);
        updateProductPriceGBP(product,updatedProductDTO);
        updateProductPriceUSD(product,updatedProductDTO);
        updateProductCategory(product, updatedProductDTO);

        productRepository.save(product);

        return mapper.map(product, ProductDTO.class);
    }

    void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    private void updateProductName(Product product, UpdateProductDTO updateProductDTO) {
        if (!product.getName().equals(updateProductDTO.getName())) {
            product.setName(updateProductDTO.getName());
        }
    }

    private void updateProductDescription(Product product, UpdateProductDTO updateProductDTO) {
        if (!product.getDescription().equals(updateProductDTO.getDescription())) {
            product.setDescription(updateProductDTO.getDescription());
        }
    }

    private void updateProductFeatures(Product product, UpdateProductDTO updateProductDTO) {
        if (!product.getFeatures().equals(updateProductDTO.getFeatures())) {
            product.setFeatures(updateProductDTO.getFeatures());
        }
    }

    private void updateProductPriceEUR(Product product, UpdateProductDTO updateProductDTO) {
        if (product.getPriceEUR() != updateProductDTO.getPriceEUR()) {
            product.setPriceEUR(updateProductDTO.getPriceEUR());
        }
    }

    private void updateProductPriceGBP(Product product, UpdateProductDTO updateProductDTO) {
        if (product.getPriceGBP() != updateProductDTO.getPriceGBP()) {
            product.setPriceGBP(updateProductDTO.getPriceGBP());
        }
    }

    private void updateProductPriceUSD(Product product, UpdateProductDTO updateProductDTO) {
        if (product.getPriceUSD() != updateProductDTO.getPriceUSD()) {
            product.setPriceUSD(updateProductDTO.getPriceUSD());
        }
    }

    private void updateProductCategory(Product product,
                                       UpdateProductDTO updateProductDTO) {
        if (product.getCategory().getId() != updateProductDTO.getCategoryId()) {
            Category newCategory =
                    categoryRepository.findById(updateProductDTO.getCategoryId()).orElseThrow(RuntimeException::new);
            product.setCategory(newCategory);
        }
    }

}
