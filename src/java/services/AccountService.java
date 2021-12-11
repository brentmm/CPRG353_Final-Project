package services;

import dataaccess.ItemsDB;
import dataaccess.UserDB;
import java.util.List;
import models.Item;
import models.User;

/**
 *
 * @author Dynamic Duo
 */
public class AccountService {

    public User get(String email) throws Exception {
        UserDB userDB = new UserDB();
        User user = null;

        try {
            user = userDB.get(email);
            
        } catch(Exception ex) {
            
        }

        return user;
    }

    public List<User> getAll() throws Exception {
        UserDB userDB = new UserDB();
        List<User> users = userDB.getAll();
        return users;

    }

    public void insert(String email, Boolean isActive, String password, String fName, String lName, int userRole) throws Exception {
        User user = new User(email, isActive, fName, lName, password, userRole);
        UserDB userDB = new UserDB();
        userDB.insert(user);

    }

    public void update(String email, Boolean isActive, String password, String fName, String lName, int userRole) throws Exception {
        User user = new User(email, isActive, fName, lName, password, userRole);
        UserDB userDB = new UserDB();
        userDB.update(user);

    }

    public void delete(String email) throws Exception {
        UserDB userDB = new UserDB();
        ItemsDB itemDB = new ItemsDB();
        
        List<Item> userItems = itemDB.get(email);
        
        for (int i = 0; i < userItems.size(); i++){
            Item item = userItems.get(i);
            int itemId = item.getItem_id();
            itemDB.delete(itemId);
        }
          
        
        userDB.delete(email);
        
    }

}
