package sb.ecomm.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    Iterable<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    Category addNewCategory(Category newCategory) {
        return categoryRepository.save(newCategory);
    }

    Optional<Category> findCategoryById(Long id) {
       Optional<Category> category = categoryRepository.findById(id);
       if (category.isPresent()) {
           return category;
       } else {
           throw new RuntimeException();
       }
    }

    Category updateCategory(Long id, Category updatedCategory) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            category.get().setName(updatedCategory.getName());
            category.get().setDescription(updatedCategory.getDescription());
            return categoryRepository.save(category.get());
        } else {
            throw new RuntimeException();
        }
    }

    void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }
}
