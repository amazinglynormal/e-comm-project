package sb.ecomm.product;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sb.ecomm.category.Category;
import sb.ecomm.category.CategoryRepository;
import sb.ecomm.product.dto.CreateProductDTO;
import sb.ecomm.product.dto.ProductDTO;
import sb.ecomm.product.dto.UpdateProductDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    ProductDTO findProductById(Long id) {
        Product product =
                productRepository.findById(id).orElseThrow(RuntimeException::new);
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
                categoryRepository.findById(newProductDto.getCategoryId()).orElseThrow(RuntimeException::new);
        newProduct.setCategory(category);
        Product savedProduct = productRepository.save(newProduct);
        return mapper.map(savedProduct, ProductDTO.class);
    }

    ProductDTO updateProduct(Long id, UpdateProductDTO updatedProductDTO) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setName(updatedProductDTO.getName());
            product.setDescription(updatedProductDTO.getDescription());
            product.setFeatures(updatedProductDTO.getFeatures());
            product.setPriceAUD(updatedProductDTO.getPriceAUD());
            product.setPriceCAD(updatedProductDTO.getPriceCAD());
            product.setPriceEUR(updatedProductDTO.getPriceEUR());
            product.setPriceGBP(updatedProductDTO.getPriceGBP());
            product.setPriceUSD(updatedProductDTO.getPriceUSD());
            Product savedProduct = productRepository.save(product);
            return mapper.map(savedProduct, ProductDTO.class);
        } else {
            throw new RuntimeException();
        }
    }

    void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }
}
