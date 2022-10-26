package com.library.citadel_library.controller;

import com.library.citadel_library.domain.Category;
import com.library.citadel_library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;



    // Get Categories With Pages
    @GetMapping()
    public ResponseEntity<Page<Category>> getCategoryWithPage(
            @RequestParam(required = false,value = "page", defaultValue = "0") int page,
            @RequestParam(required = false,value = "size", defaultValue = "20") int size,
            @RequestParam(required = false,value = "sort", defaultValue = "name") String prop,
            @RequestParam(required = false,value = "type", defaultValue = "ASC") Sort.Direction type){

        Pageable pageable = PageRequest.of(page,size,Sort.by(type,prop));
        Page<Category> catetegoryPage = categoryService.getAllWithPage(pageable);

        return ResponseEntity.ok(catetegoryPage);
    }

    //Get Category With Id
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Long id){
        Category category =  categoryService.getCategory(id);

        return new ResponseEntity<>(category,HttpStatus.OK);
    }


    //Create Catetory
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<Category> createCategory(@Valid @RequestBody Category category){
        categoryService.saveCategory(category);

        return new ResponseEntity<>(category,HttpStatus.CREATED);
    }

    // Update Category
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @Valid @RequestBody Category category){
        categoryService.updateCategory(id,category);

        return new ResponseEntity<>(category,HttpStatus.OK);
    }

    //Delete Category
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable Long id){
      Category category =  categoryService.deleteCategory(id);

        return new ResponseEntity<>(category,HttpStatus.OK);
    }



}
