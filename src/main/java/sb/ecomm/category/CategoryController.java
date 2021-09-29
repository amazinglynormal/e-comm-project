package sb.ecomm.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public Iterable<CategoryDTO> getAllCategories() {
        return categoryService.findAllCategories();
    }

    @PostMapping
    public CategoryDTO addNewCategory(@RequestBody CreateCategoryDTO newCategory) {
        return categoryService.addNewCategory(newCategory);
    }

    @GetMapping("/{id}")
    public CategoryDTO findCategoryById(@PathVariable long id) {
        return categoryService.findCategoryById(id);
    }

    @PutMapping("/{id}")
    public CategoryDTO updateCategory(@PathVariable long id,
                                             @RequestBody UpdateCategoryDTO updatedCategory) {
        return categoryService.updateCategory(id, updatedCategory);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable long id) {
        categoryService.deleteCategoryById(id);
    }
}
