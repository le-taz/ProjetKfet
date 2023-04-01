package com.projetkfet.backend.controller.stock;

import com.projetkfet.backend.data.stock.CategoryRepository;
import com.projetkfet.backend.dto.ImageDTO;
import com.projetkfet.backend.projection.stock.CategoryProjection;
import com.projetkfet.backend.model.stock.Category;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping(path="/category")
public class CategoryController {

    private static final Logger logger = LogManager.getLogger("ProductLogger");

    @Autowired
    private CategoryRepository categoryRepository;

//    GET

//    Retourne la liste de toutes les catégories
    @GetMapping(path="/all")
    public @ResponseBody
    List<CategoryProjection> getAllCategories() throws Exception {
        return categoryRepository.findAllProjectedBy();
    }

    @GetMapping()
    public @ResponseBody Category getCategory(@RequestParam("id") String id) throws Exception {
        logger.info("Get category");

        Optional<Category> c = categoryRepository.findById(UUID.fromString(id));

        Category category = null;
        // if n est non null
        if (c.isPresent()) {
            category = c.get();
        }
        else
        {
            logger.info("Category doesn't exist");
            throw new Exception("Category doesn't exist");
        }
        return category;
    }

//    POST

//    Permet d'ajouter une nouvelle catégorie
    @PostMapping(path="/add")
    public @ResponseBody
    UUID addNewCategory (@RequestParam("name") String name, @RequestBody(required = false)ImageDTO image) throws Exception {
        logger.info("New Categorie : " + name);

        UUID id = null;

        Category c = new Category();
        c.setName(name);
        if (image != null && image.getImage() != null) {
            c.setImage(image.getImage());
        }
        categoryRepository.save(c);

        id = c.getId();
        if (id == null)
        {
            logger.info("Category not added");
            throw new Exception("Category not added");
        }
        logger.info("Category added : " + id);
        return id;
    }

//    UPDATE

    @PatchMapping()
    public @ResponseBody
    String updateCategory(@RequestParam("id") String id, @RequestParam(required = false, name="name") String name, @RequestBody(required = false)ImageDTO image) throws Exception {
        logger.info("Update Category");

        Optional<Category> c = categoryRepository.findById(UUID.fromString(id));

        if (c.isPresent())
        {
            Category category = c.get();
            if (name != null && !name.isEmpty())
                category.setName(name);
            if (image != null && image.getImage() != null) {
                category.setImage(image.getImage());
            }
            categoryRepository.save(category);
            logger.info("Category updated : " + id);
            return "Confirm";
        }
        else
        {
            logger.info("No category for this ID");
            throw new Exception("No category for this ID");
        }
    }

//    DELETE

    @DeleteMapping()
    public @ResponseBody
    String deleteCategory(@RequestParam("id") String id) throws Exception {
        logger.info("Delete User");
        Optional<Category> c = categoryRepository.findById(UUID.fromString(id));

        if (c.isPresent())
        {
            logger.info("Category deleted : " + id);
            categoryRepository.delete(c.get());
            return "Confirm";
        }
        else
        {
            logger.info("No category for this ID");
            throw new Exception("No category for this ID");
        }

    }
}
