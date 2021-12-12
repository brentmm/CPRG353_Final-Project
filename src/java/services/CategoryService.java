package services;

import dataaccess.CategoriesDB;
import java.util.List;
import models.Category;

public class CategoryService {

    public List<Category> getAll() throws Exception {
        CategoriesDB categoryDB = new CategoriesDB();
        List<Category> categories = categoryDB.getAll();
        return categories;
    }

    public Category get(int categoryId) throws Exception {
        CategoriesDB categoryDB = new CategoriesDB();
        Category category = categoryDB.get(categoryId);
        return category;

    }

    public void insert(int categoryId, String categoryNme) throws Exception {
        CategoriesDB categoryDB = new CategoriesDB();
        categoryDB.insert(categoryId, categoryNme);

    }

    public void update(int categoryId, String categoryNme) throws Exception {
        CategoriesDB categoryDB = new CategoriesDB();
        categoryDB.update(categoryId, categoryNme);

    }

}
