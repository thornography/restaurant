package com.gencprogramcilar.controller;

import com.gencprogramcilar.model.Category;
import com.gencprogramcilar.service.CategoryService;

import java.sql.SQLException;
import java.util.List;

public class CategoryController {
    public CategoryController() {
    }

    CategoryService service = new CategoryService();
    public void add(String name){
        Category category = new Category();
        category.setName(name);
        service.add(category);
    }

    public void edit(String name){
        Category category = new Category();
        category.setName(name);
        service.edit(category);
    }

    public void delete(int id){
        service.delete(id);
    }

    public List<Category> get() throws SQLException {
        List<Category> categories=service.get();
        return categories;
    }
}
