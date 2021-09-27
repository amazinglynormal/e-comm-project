package sb.ecomm.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public Iterable<Category> getAllCategories() {
        return categoryService.findAllCategories();
    }

    @PostMapping
    public Category addNewCategory(@RequestBody Category newCategory) {
        return categoryService.addNewCategory(newCategory);
    }

    @GetMapping("/{id}")
    public Optional<Category> findCategoryById(@PathVariable long id) {
        return categoryService.findCategoryById(id);
    }

    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable long id,
                                             @RequestBody Category updatedCategory) {
        return categoryService.updateCategory(id, updatedCategory);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable long id) {
        categoryService.deleteCategoryById(id);
    }
}
