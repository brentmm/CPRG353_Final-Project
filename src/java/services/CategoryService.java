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

    public Category get(int categoryId ) throws Exception {
        CategoriesDB categoryDB = new CategoriesDB();
        Category category = categoryDB.get(categoryId);
        return category;

    }

//    public Item get(int itemId) throws Exception {
//        ItemsDB itemDB = new ItemsDB();
//        Item item = itemDB.get(itemId);
//        return item;
//    }
//
//    public void insert(int itemId, int category, String itemName, double itemPrice, String owner) throws Exception {
//        ItemsDB itemDB = new ItemsDB();
//        itemDB.insert(itemId, category, itemName, itemPrice, owner);
//
//    }
//
//    public void update(int itemId, int category, String itemName, double itemPrice, String owner) throws Exception {
//        Item item = new Item(itemId, category, itemName, itemPrice, owner);
//        ItemsDB itemDB = new ItemsDB();
//        itemDB.update(item);
//
//    }
//
//    public void delete(int itemId) throws Exception {
//        ItemsDB itemDB = new ItemsDB();
//        itemDB.delete(itemId);
//    }

}
