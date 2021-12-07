package sb.ecomm.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import sb.ecomm.category.Category;

import java.util.Collection;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
    Page<Product> findProductsByCategoryIn(Collection<Category> categories,
                                           Pageable pageable);

    Page<Product> findProductsByCategoryInAndColorInAndAvailableSizesIn(Collection<Category> categories, Collection<Color> colors, Collection<String> sizes, Pageable pageable);

    Page<Product> findProductsByCategoryInAndAvailableSizesIn(Collection<Category> categories, Collection<String> sizes, Pageable pageable);

    Page<Product> findProductsByCategoryInAndColorIn(Collection<Category> categories, Collection<Color> colors,
                                                  Pageable pageable);

}
