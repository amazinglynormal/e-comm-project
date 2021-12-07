package sb.ecomm.product;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sb.ecomm.category.Category;
import sb.ecomm.exceptions.CategoryNameNotFoundException;
import sb.ecomm.exceptions.CategoryNotFoundException;
import sb.ecomm.category.CategoryRepository;
import sb.ecomm.exceptions.ProductNotFoundException;
import sb.ecomm.product.dto.CreateProductDTO;
import sb.ecomm.product.dto.ProductDTO;
import sb.ecomm.product.dto.UpdateProductDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, ModelMapper mapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    Iterable<ProductDTO> findProductsByCategoriesAndColorsAndSizes(List<String> categories,
                                                                   List<String> colors,
                                                                   List<String> sizes,
                                                                   int page) {
        List<Category> categoryList =
                convertCategoryNamesIntoCategoryList(categories);
        List<Color> colorsList = convertStringsToColors(colors);
        Pageable pageable = PageRequest.of(page, 15);

        Iterable<Product> products =
                productRepository.findProductsByCategoryInAndColorInAndAvailableSizesIn(categoryList, colorsList, sizes, pageable);

        return convertProductsToProductDTOs(products);
    }

    Iterable<ProductDTO> findProductsByCategories(List<String> categories, int page) {
        List<Category> categoryList = convertCategoryNamesIntoCategoryList(categories);
        Pageable pageable = PageRequest.of(page, 15);
        Iterable<Product> products =
                productRepository.findProductsByCategoryIn(categoryList,
                        pageable);

        return convertProductsToProductDTOs(products);
    }

    Iterable<ProductDTO>findProductsByCategoriesAndColors(List<String> categories,
                                                          List<String> colors,
                                                          int page) {
        List<Category> categoryList =
                convertCategoryNamesIntoCategoryList(categories);
        List<Color> colorsList = convertStringsToColors(colors);
        Pageable pageable = PageRequest.of(page, 15);

        Iterable<Product> products =
                productRepository.findProductsByCategoryInAndColorIn(categoryList, colorsList, pageable);

        return convertProductsToProductDTOs(products);
    }

    Iterable<ProductDTO>findProductsByCategoriesAndSizes(List<String> categories,
                                                         List<String> sizes,
                                                         int page) {

        List<Category> categoryList =
                convertCategoryNamesIntoCategoryList(categories);
        Pageable pageable = PageRequest.of(page, 15);

        Iterable<Product> products =
                productRepository.findProductsByCategoryInAndAvailableSizesIn(categoryList, sizes, pageable);

        return convertProductsToProductDTOs(products);
    }


    ProductDTO findProductById(Long id) {
        Product product =
                productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        return mapper.map(product, ProductDTO.class);
    }

    ProductDTO addNewProduct(CreateProductDTO newProductDto) {
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
        updateProductName(product, updatedProductDTO);
        updateProductDescription(product, updatedProductDTO);
        updateProductPriceEUR(product, updatedProductDTO);
        updateProductPriceGBP(product, updatedProductDTO);
        updateProductPriceUSD(product, updatedProductDTO);
        updateProductCategory(product, updatedProductDTO);
        updateProductColor(product, updatedProductDTO);
        updateProductImageSrc(product, updatedProductDTO);
        updateProductImageAlt(product, updatedProductDTO);
        updateProductAllSizes(product, updatedProductDTO);
        updateProductAvailableSizes(product, updatedProductDTO);

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

    private void updateProductPriceEUR(Product product, UpdateProductDTO updateProductDTO) {
        if (product.getEUR() != updateProductDTO.getEUR()) {
            product.setEUR(updateProductDTO.getEUR());
        }
    }

    private void updateProductPriceGBP(Product product, UpdateProductDTO updateProductDTO) {
        if (product.getGBP() != updateProductDTO.getGBP()) {
            product.setGBP(updateProductDTO.getGBP());
        }
    }

    private void updateProductPriceUSD(Product product, UpdateProductDTO updateProductDTO) {
        if (product.getUSD() != updateProductDTO.getUSD()) {
            product.setUSD(updateProductDTO.getUSD());
        }
    }

    private void updateProductCategory(Product product,
                                       UpdateProductDTO updateProductDTO) {
        if (product.getCategory().getId().equals(updateProductDTO.getCategoryId())) {
            Category newCategory =
                    categoryRepository.findById(updateProductDTO.getCategoryId()).orElseThrow(RuntimeException::new);
            product.setCategory(newCategory);
        }
    }

    private void updateProductColor(Product product,
                                    UpdateProductDTO updateProductDTO) {
        if (!product.getColor().equals(updateProductDTO.getColor())) {
            product.setColor(updateProductDTO.getColor());
        }
    }

    private void updateProductImageSrc(Product product,
                                       UpdateProductDTO updateProductDTO) {
        if (!product.getImageSrc().equals(updateProductDTO.getImageSrc())) {
            product.setImageSrc(updateProductDTO.getImageSrc());
        }
    }

    private void updateProductImageAlt(Product product,
                                       UpdateProductDTO updateProductDTO) {
        if(!product.getImageAlt().equals(updateProductDTO.getImageAlt())) {
            product.setImageAlt(updateProductDTO.getImageAlt());
        }
    }

    private void updateProductAllSizes(Product product,
                                       UpdateProductDTO updateProductDTO) {
        if (!compareStringLists(product.getAllSizes(),
                updateProductDTO.getAllSizes())) {
            product.setAllSizes(updateProductDTO.getAllSizes());
        }
    }

    private void updateProductAvailableSizes(Product product,
                                             UpdateProductDTO updateProductDTO) {
        if (!compareStringLists(product.getAvailableSizes(),
                updateProductDTO.getAvailableSizes())) {
            product.setAvailableSizes(updateProductDTO.getAvailableSizes());
        }
    }

    private List<Category> convertCategoryNamesIntoCategoryList(List<String> names) {
        List<Category> categoryList = new ArrayList<>();
        names.forEach(name -> {
            Category category =
                    categoryRepository.findByName(name).orElseThrow(() -> new CategoryNameNotFoundException(name));
            categoryList.add(category);
        });

        return categoryList;
    }

    private List<Color> convertStringsToColors(List<String> colorStrings) {
        List<Color> colors = new ArrayList<>();
        colorStrings.forEach(colorString -> colors.add(Color.valueOf(colorString)));
            
        return colors;
    }

    private List<ProductDTO> convertProductsToProductDTOs(Iterable<Product> products) {
        List<ProductDTO> productDTOs = new ArrayList<>();
        products.forEach(product -> productDTOs.add(mapper.map(product, ProductDTO.class)));
        return productDTOs;
    }

    private boolean compareStringLists(List<String> list1,
                                       List<String> list2) {
        if (list1.size() != list2.size()) {
            return false;
        }

        List<String> copy1 = new ArrayList<>(list1);
        List<String> copy2 = new ArrayList<>(list2);

        Collections.sort(copy1);
        Collections.sort(copy2);

        return copy1.equals(copy2);
    }

}
