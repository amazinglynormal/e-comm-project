package sb.ecomm.category;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private ModelMapper mapper;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, ModelMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    Iterable<CategoryDTO> findAllCategories() {
        Iterable<Category> categories = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOs = new ArrayList<>();
        categories.forEach(category -> categoryDTOs.add(mapper.map(category, CategoryDTO.class)));

        return categoryDTOs;
    }

    CategoryDTO addNewCategory(CreateCategoryDTO newCategoryDTO) {
        Category newCategory = mapper.map(newCategoryDTO, Category.class);
        Category savedCategory = categoryRepository.save(newCategory);
        return mapper.map(savedCategory, CategoryDTO.class);
    }

    CategoryDTO findCategoryById(Long id) {
       Category category =
               categoryRepository.findById(id).orElseThrow(RuntimeException::new);
       return mapper.map(category, CategoryDTO.class);
    }

    CategoryDTO updateCategory(Long id, UpdateCategoryDTO updatedCategoryDTO) {
        Category updatedCategory =  mapper.map(updatedCategoryDTO,
                Category.class);
        Category category =
                categoryRepository.findById(id).orElseThrow(RuntimeException::new);
        category.setName(updatedCategory.getName());
        category.setDescription(updatedCategory.getDescription());

        Category savedCategory = categoryRepository.save(category);
        return mapper.map(savedCategory, CategoryDTO.class);
    }

    void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }
}
