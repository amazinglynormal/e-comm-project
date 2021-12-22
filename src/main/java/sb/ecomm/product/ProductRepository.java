package sb.ecomm.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sb.ecomm.category.Category;

import java.util.Collection;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
    @Query(
            "SELECT p FROM Product p WHERE p.id IN " +
                    "(SELECT MIN(p.id) FROM Product p WHERE " +
                    "p.category IN (:categories) GROUP BY p.name)"
    )
    Page<Product> findProductsByCategoryIn(@Param("categories") Collection<Category> categories,
                                           Pageable pageable);

    @Query(
            "SELECT p FROM Product p WHERE p.id IN " +
                    "(SELECT MIN(p.id) FROM Product p WHERE " +
                    "p.category IN (:categories) AND p.color IN (:colors) " +
                    "AND" +
                    " p.size IN (:sizes) GROUP BY p.name)"
    )
    Page<Product> findProductsByCategoryInAndColorInAndSizeIn(@Param("categories") Collection<Category> categories,
                                                              @Param("colors") Collection<Color> colors,
                                                              @Param("sizes") Collection<String> sizes,
                                                              Pageable pageable);

    @Query(
            "SELECT p FROM Product p WHERE p.id IN " +
                    "(SELECT MIN(p.id) FROM Product p WHERE " +
                    "p.category IN (:categories) AND" +
                    " p.size IN (:sizes) GROUP BY p.name)"
    )
    Page<Product> findProductsByCategoryInAndSizeIn(@Param("categories") Collection<Category> categories,
                                                    @Param("sizes") Collection<String> sizes,
                                                    Pageable pageable);

    @Query(
            "SELECT p FROM Product p WHERE p.id IN " +
                    "(SELECT MIN(p.id) FROM Product p WHERE " +
                    "p.category IN (:categories) AND p.color IN (:colors) " +
                    "GROUP BY p.name)"
    )
    Page<Product> findProductsByCategoryInAndColorIn(@Param("categories") Collection<Category> categories,
                                                     @Param("colors") Collection<Color> colors,
                                                     Pageable pageable);

    Page<Product> findProductsByNameAndCategoryAndColor(String name,
                                                        Category category,
                                                        Color color,
                                                        Pageable pageable);
}