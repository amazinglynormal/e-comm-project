package sb.ecomm.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sb.ecomm.product.dto.CreateProductDTO;
import sb.ecomm.product.dto.ProductDTO;
import sb.ecomm.product.dto.QueryResults;
import sb.ecomm.product.dto.UpdateProductDTO;

import java.util.List;
import java.util.Map;
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
    public QueryResults getAllProducts(
            @RequestParam(name = "categories") List<String> categories,
            @RequestParam(name = "colors") Optional<List<String>> colors,
            @RequestParam(name = "sizes") Optional<List<String>> sizes,
            @RequestParam(name = "page") int page
    ) {

        if (colors.isPresent() && sizes.isPresent()) {
            return productService.findProductsByCategoriesAndColorsAndSizes(categories, colors.get(), sizes.get(), page);
        } else if (colors.isPresent()) {
            return productService.findProductsByCategoriesAndColors(categories, colors.get(), page);
        } else if (sizes.isPresent()) {
            return productService.findProductsByCategoriesAndSizes(categories
                    , sizes.get(), page);
        }

        return productService.findProductsByCategories(categories, page);
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
