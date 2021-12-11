package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import models.Category;
import models.Item;

public class ItemsDB {

    public List<Item> get(String email) throws Exception {
        List<Item> items = new ArrayList<>();
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM item WHERE owner=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();
            CategoriesDB itemCategory = new CategoriesDB();
            while (rs.next()) {
                int itemId = rs.getInt(1);
                Category category = itemCategory.get(rs.getInt(2));
                String itemName = rs.getString(3);
                double itemPrice = rs.getDouble(4);
                String owner = rs.getString(5);

                Item item = new Item(itemId, category, itemName, itemPrice, owner);
                items.add(item);
            }
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }

        return items;
    }

    public Item get(int item) throws Exception {
        Item userItem = null;
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        CategoriesDB itemCategory = new CategoriesDB();

        String sql = "SELECT * FROM item WHERE item_Id=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, item);
            rs = ps.executeQuery();
            if (rs.next()) {
                int itemId = rs.getInt(1);
                Category category = itemCategory.get(rs.getInt(2));
                String itemName = rs.getString(3);
                double itemPrice = rs.getDouble(4);
                String owner = rs.getString(5);

                userItem = new Item(itemId, category, itemName, itemPrice, owner);
            }

        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }

        return userItem;

    }

    public List<Item> getAll() throws Exception {
        List<Item> items = new ArrayList<>();
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM item";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            CategoriesDB itemCategory = new CategoriesDB();
            while (rs.next()) {
                int itemId = rs.getInt(1);
                Category category = itemCategory.get(rs.getInt(2));
                String itemName = rs.getString(3);
                double itemPrice = rs.getDouble(4);
                String owner = rs.getString(5);
                Item item = new Item(itemId, category, itemName, itemPrice, owner);
                items.add(item);
            }
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }

        return items;
    }

    public void insert(int itemId, int category, String itemName, double itemPrice, String owner) throws Exception {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        String sql = "INSERT INTO item (item_Id, category, item_Name, Price, owner) VALUES (?, ?, ?, ?, ?)";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, itemId);
            ps.setInt(2, category);
            ps.setString(3, itemName);
            ps.setDouble(4, itemPrice);
            ps.setString(5, owner);
            ps.executeUpdate();
        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
    }

    public void update(Item item) throws Exception {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        String sql = "UPDATE item SET Category=?, item_Name=?, Price=? WHERE item_Id=?";

        try {
            ps = con.prepareStatement(sql);
//            ps.setInt(1, item.category.getCategoryId());
            ps.setInt(1, item.category.getCategory_id());
            ps.setString(2, item.getItem_name());
            ps.setDouble(3, item.getPrice());
            ps.setInt(4, item.getItem_id());
            ps.executeUpdate();
        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
    }

    public void delete(int itemId) throws Exception {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        String sql = "DELETE FROM item WHERE item_Id=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, itemId);
            ps.executeUpdate();
        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
    }

}
