package sb.ecomm.product;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sb.ecomm.category.Category;
import sb.ecomm.constants.TempSecurityConstants;
import sb.ecomm.exceptions.CategoryNameNotFoundException;
import sb.ecomm.exceptions.CategoryNotFoundException;
import sb.ecomm.category.CategoryRepository;
import sb.ecomm.exceptions.ProductNotFoundException;
import sb.ecomm.product.dto.CreateProductDTO;
import sb.ecomm.product.dto.ProductDTO;
import sb.ecomm.product.dto.QueryResults;
import sb.ecomm.product.dto.UpdateProductDTO;


import java.util.*;

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

    QueryResults findProductsByCategoriesAndColorsAndSizes(List<String> categories,
                                                                   List<String> colors,
                                                                   List<String> sizes,
                                                                   int page) {
        List<Category> categoryList =
                convertCategoryNamesIntoCategoryList(categories);
        List<Color> colorsList = convertStringsToColors(colors);
        Pageable pageable = PageRequest.of(page, 15);

        Page<Product> products =
                productRepository.findProductsByCategoryInAndColorInAndSizeIn(categoryList, colorsList, sizes, pageable);

        List<ProductDTO> productDTOS = convertProductsToProductDTOs(products);

        return new QueryResults(productDTOS,
                products.getNumberOfElements(),
                products.getTotalPages());
    }

    QueryResults findProductsByCategories(List<String> categories, int page) {
        List<Category> categoryList = convertCategoryNamesIntoCategoryList(categories);
        Pageable pageable = PageRequest.of(page, 15);
        Page<Product> products =
                productRepository.findProductsByCategoryIn(categoryList,
                        pageable);

        List<ProductDTO> productDTOS = convertProductsToProductDTOs(products);

        return new QueryResults(productDTOS,
                products.getNumberOfElements(),
                products.getTotalPages());
    }

    QueryResults findProductsByCategoriesAndColors(List<String> categories,
                                                          List<String> colors,
                                                          int page) {
        List<Category> categoryList =
                convertCategoryNamesIntoCategoryList(categories);
        List<Color> colorsList = convertStringsToColors(colors);
        Pageable pageable = PageRequest.of(page, 15);

        Page<Product> products =
                productRepository.findProductsByCategoryInAndColorIn(categoryList, colorsList, pageable);

        List<ProductDTO> productDTOS = convertProductsToProductDTOs(products);

        return new QueryResults(productDTOS,
                products.getNumberOfElements(),
                products.getTotalPages());
    }

    QueryResults findProductsByCategoriesAndSizes(List<String> categories,
                                                         List<String> sizes,
                                                         int page) {

        List<Category> categoryList =
                convertCategoryNamesIntoCategoryList(categories);
        Pageable pageable = PageRequest.of(page, 15);

        Page<Product> products =
                productRepository.findProductsByCategoryInAndSizeIn(categoryList, sizes, pageable);

        List<ProductDTO> productDTOS = convertProductsToProductDTOs(products);

        return new QueryResults(productDTOS,
                products.getNumberOfElements(),
                products.getTotalPages());
    }

    Iterable<ProductDTO> findAlternativeSizesForProduct(Long id) {
        Product product =
                productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

        Pageable pageable = PageRequest.of(0, 30);

        Page<Product> matchingProducts =
                productRepository.findProductsByNameAndCategoryAndColor(product.getName(),
                        product.getCategory(), product.getColor(), pageable);

        return convertProductsToProductDTOs(matchingProducts);
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

//        addProductToStripeAccount(savedProduct);

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
        updateProductSize(product, updatedProductDTO);
        updateProductStockRemaining(product, updatedProductDTO);

        productRepository.save(product);

        return mapper.map(product, ProductDTO.class);
    }

    void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    private void addProductToStripeAccount(Product product) {
        Stripe.apiKey = TempSecurityConstants.stripeTestKey;

        try {
            Map<String, Object> productParams = new HashMap<>();
            productParams.put("name", product.getName());
            productParams.put("description", product.getDescription());
            productParams.put("shippable", true);

            com.stripe.model.Product stripeProduct = com.stripe.model.Product.create(productParams);

            String[] currencies = {"eur", "gbp", "usd"};

            for (String currency: currencies) {
                Map<String, Object> priceParams = new HashMap<>();
                priceParams.put("product", stripeProduct.getId());
                priceParams.put("currency", currency);
                priceParams.put("billing_scheme", "per_unit");

                double price;

                switch(currency) {
                    case "gbp":
                        price = product.getGBP();
                        break;
                    case "usd":
                        price = product.getUSD();
                                break;
                    case "eur":
                    default:
                        price = product.getEUR();
                        break;
                }

                double priceInCents = price * 100;
                int unitAmount = (int) priceInCents;
                priceParams.put("unit_amount", unitAmount);

                com.stripe.model.Price stripePrice = com.stripe.model.Price.create(priceParams);

                switch(currency) {
                    case "gbp":
                        product.setStripeGbp(stripePrice.getId());
                        break;
                    case "usd":
                        product.setStripeUsd(stripePrice.getId());
                        break;
                    case "eur":
                    default:
                        product.setStripeEur(stripePrice.getId());
                        break;
                }
            }

        } catch (StripeException ex) {
            throw new RuntimeException("Could not add product and price to Stripe");
        }


        productRepository.save(product);

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

    private void updateProductSize(Product product,
                                       UpdateProductDTO updateProductDTO) {
        if (!product.getSize().equals(updateProductDTO.getSize())) {
            product.setSize(updateProductDTO.getSize());
        }
    }

    private void updateProductStockRemaining(Product product,
                                             UpdateProductDTO updateProductDTO) {

        if (product.getStockRemaining() != updateProductDTO.getStockRemaining()) {
            product.setStockRemaining(updateProductDTO.getStockRemaining());
        }

        updateStockAvailability(product);
    }

    private void updateStockAvailability(Product product) {
        if (product.getStockRemaining() == 0) {
            product.setInStock(false);
        } else {
            product.setInStock(true);
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

    private List<ProductDTO> convertProductsToProductDTOs(Page<Product> products) {
        List<ProductDTO> productDTOs = new ArrayList<>();
        products.forEach(product -> productDTOs.add(mapper.map(product, ProductDTO.class)));
        return productDTOs;
    }

}
