package sb.ecomm.category;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
}
