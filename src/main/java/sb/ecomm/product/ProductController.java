package sb.ecomm.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sb.ecomm.product.dto.CreateProductDTO;
import sb.ecomm.product.dto.ProductDTO;
import sb.ecomm.product.dto.UpdateProductDTO;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Iterable<ProductDTO> getAllProducts(@RequestParam("name") Optional<String> name) {
        if (name.isPresent()) {
            return productService.findProductByName(name.get());
        }

        return productService.findAllProducts();
    }

    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable long id) {
        return productService.findProductById(id);
    }


    @PostMapping
    public ProductDTO addNewProduct(@RequestBody CreateProductDTO newProductDto) {
        return productService.addNewProduct(newProductDto);
    }

    @PutMapping("/{id}")
    public ProductDTO updateProductById(@PathVariable long id,
                                     @RequestBody UpdateProductDTO updateProductDTO) {
        return productService.updateProduct(id, updateProductDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable long id) {
        productService.deleteProductById(id);
    }
}
