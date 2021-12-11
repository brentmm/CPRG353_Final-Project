package models;

public class Item {
    private int item_id;
    public Category category;
    private String item_name;
    private double price;
    private String owner;

    public Item() {
    }

    public Item(int item_id, Category category, String item_name, double price, String owner) {
        this.item_id = item_id;
        this.category = category;
        this.item_name = item_name;
        this.price = price;
        this.owner = owner;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
    
    
    
}
