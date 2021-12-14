package servlets;

import dataaccess.ItemsDB;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Item;
import models.User;
import services.AccountService;
import services.InventoryService;

public class InventoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        //gets session
        HttpSession session = request.getSession();

        //sets local var to session value
        String username = (String) session.getAttribute("sessionUser");

        //checks if username is null if true forces login
        if (username == null || username.equals("")) {
            response.sendRedirect("login"); //redirects to login page
            return;
        }

        InventoryService is = new InventoryService();
        AccountService as = new AccountService();

        request.setAttribute("username", username); //sets username to display on web page
        String action = request.getParameter("action");
        String email = request.getParameter("email");
        String itemId = request.getParameter("itemId");

        if (action != null) {

            if (action.equals("editUser")) {
                if (email.contains(" ")) {
                    String emailArray[] = email.split(" ");
                    String emailMake = emailArray[0] + "+" + emailArray[1];
                    email = emailMake;
                }
                try {
                    User user = as.get(email);
                    request.setAttribute("editEmail", email); //sets all input boxes to email users info
                    request.setAttribute("editPassword", user.getPassword());
                    request.setAttribute("editFirstName", user.getFirstName());
                    request.setAttribute("editLastName", user.getLastName());
                    request.setAttribute("editActive", user.getActiveStatus());
                    request.setAttribute("editIsAdmin", user.getRole());

                } catch (Exception ex) {

                }
            }

            if (action.equals("editItem")) {
                int int_itemId = Integer.parseInt(itemId);

                try {
                    Item userItem = is.get(int_itemId); //reloads table from db
                    request.setAttribute("editCategory", userItem.category.getCategory_name()); //sets all input boxes to email users info
                    session.setAttribute("sess_Category", userItem.category.getCategory_name());
                    request.setAttribute("editItem", userItem.getItem_name());
                    request.setAttribute("editPrice", userItem.getPrice());

                } catch (Exception ex) {

                }
            }

            if (action.equals("deleteItem")) {
                int int_itemId = Integer.parseInt(itemId);

                try {
                    Item userItem = is.get(int_itemId); //reloads table from db

                    if (username.equals(userItem.getOwner())) {
                        is.delete(int_itemId);// if action = deleted users is removed from db by email
                        List<Item> itemsList = is.get(username); //reloads table from db
                        request.setAttribute("items", itemsList);

                    } else {
                        request.setAttribute("message", "Error deleting.");
                    }

                } catch (Exception ex) {
                    Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
                    request.setAttribute("message", "Error deleting.");
                }
            } else {

            }
        }

        try {
            List<Item> itemsList = is.get(username);
            request.setAttribute("items", itemsList);
        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", "Loading items");
        }

        try {
            User user = as.get(username);
            request.setAttribute("user", user);
        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", "Loading user");
        }

        getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response); //loads inventory page

        return;

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        ItemsDB itemDB = new ItemsDB();
        AccountService as = new AccountService();
        InventoryService is = new InventoryService();

        //gets session
        HttpSession session = request.getSession();

        //sets local var to session value
        String username = (String) session.getAttribute("sessionUser");

        try {
            User user = as.get(username);
            request.setAttribute("users", user);

            List<Item> allItems = itemDB.getAll();
            int numItems = allItems.size();

            String userAction = request.getParameter("userAction");

            if (userAction.equals("saveUser")) {

                try {

                    String editEmail = request.getParameter("editEmail"); //retrieves values from input boxes
                    String password = request.getParameter("editPassword");
                    String firstName = request.getParameter("editFirstName");
                    String lastName = request.getParameter("editLastName");
                    String active = request.getParameter("activeEdit");
                    String role = request.getParameter("role");

                    if (editEmail.length() <= 40 && firstName != null && !firstName.equals("") && firstName.length() <= 20 && lastName != null && !lastName.equals("") && lastName.length() <= 20 && password != null && !password.equals("") && password.length() <= 20) {
                        //if stmt checks to make sure boxes are not empty and that the name/email meets db constraints 
                        boolean isActive;
                        int int_role;

                        if (active != null) {
                            isActive = true;
                        } else {
                            isActive = false;
                        }

                        if (role != null) {
                            int_role = 1;
                        } else {
                            int_role = 2;
                        }

                        as.update(editEmail, isActive, password, firstName, lastName, int_role); //updates user in db
                        List<User> usersList = as.getAll(); //reloads updated table from db                        
                        request.setAttribute("users", usersList);
                        user = as.get(username);
                        request.setAttribute("user", user);
                        request.setAttribute("message", "User Edited!");
                    } else {
                        request.setAttribute("message", "There was an error while saving a user.");
                        try {
                            List<User> usersList1; //if there is an error that doesnt allow an update table is reloaded
                            usersList1 = as.getAll();
                            request.setAttribute("users", usersList1);
                        } catch (Exception ex) {

                        }
                    }
                } catch (Exception ex) {

                }

            }

            String categoryName = request.getParameter("category");
            String itemName = request.getParameter("item");
            String itemPrice = request.getParameter("price");
            String owner = (String) session.getAttribute("sessionUser");

            if ((userAction.equals("addItem")) || (userAction.equals("editItem"))) {

                if (userAction.equals("addItem")) {
                    int intPrice = Integer.parseInt(itemPrice); //swaps item price value into an integer

                    if (intPrice < 1 || intPrice > 10000) {
                        request.setAttribute("username", username); //sets username to display on web page
                        request.setAttribute("userCategory", categoryName);//setting values of input boxes
                        request.setAttribute("userItem", itemName);
                        request.setAttribute("userPrice", itemPrice);
                        List<Item> usersList = is.get(username); //if there is an error that doesnt allow an insert table is reloaded
                        request.setAttribute("items", usersList);

                        request.setAttribute("error", "Invalid. Please re-enter");//sets error message

                        //display form again
                        getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response);
                        //after reload stop rest of execution
                        return;
                    }

                    if (itemName != null || !itemName.equals("") || !itemName.equals("null") || itemPrice != null || !itemPrice.equals("") || itemPrice.matches("[0-9]+")) { //checking user entered valid price

                        int int_category = 0;

                        switch (categoryName) { //switch to set category on display
                            case "kitchen":
                                int_category = 1;
                                break;
                            case "bathroom":
                                int_category = 2;
                                break;
                            case "living room":
                                int_category = 3;
                                break;
                            case "basement":
                                int_category = 4;
                                break;
                            case "bedroom":
                                int_category = 5;
                                break;
                            case "garage":
                                int_category = 6;
                                break;
                            case "office":
                                int_category = 7;
                                break;
                            case "utility room":
                                int_category = 8;
                                break;
                            case "storage":
                                int_category = 9;
                                break;
                            case "other":
                                int_category = 10;
                                break;
                            default:
                                int_category = 10;
                                break;
                        }

                        double doub_itemPrice = Double.parseDouble(itemPrice);

                        //if stmt checks to make sure boxes are not empty and that the name/email meets db constraints
                        try {
                            is.insert(numItems + 1, int_category, itemName, doub_itemPrice, owner); //inserts user into db table
                            List<Item> itemsList = is.get(username); //reloads updated table from db
                            request.setAttribute("items", itemsList);
                            user = as.get(username);
                            request.setAttribute("user", user);
                            request.setAttribute("message", "Item added!");

                        } catch (Exception ex) {
                            try {
                                List<Item> usersList = is.get(username); //if there is an error that doesnt allow an insert table is reloaded
                                request.setAttribute("items", usersList);
                                request.setAttribute("message", "error");
                            } catch (Exception ex1) {

                            }
                        }

                    } else {
                        request.setAttribute("username", username); //sets username to display on web page
                        request.setAttribute("usercategory", categoryName);//setting values of input boxes
                        request.setAttribute("userItem", itemName);
                        request.setAttribute("userPrice", itemPrice);
                        List<Item> usersList = is.get(username); //if there is an error that doesnt allow an insert table is reloaded
                        request.setAttribute("items", usersList);

                        request.setAttribute("error", "Invalid. Please input cannot be empty:2!");//sets error message

                        getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response); //loads inventory page
                        return;
                    }

                } else if (userAction.equals("editItem")) {

                    String editId = request.getParameter("itemId");
                    String editCategory = (String) session.getAttribute("sess_Category");
                    String editName = request.getParameter("editItem");
                    String editPrice = request.getParameter("editPrice");
                    String editOwner = username;

                    double doub_editPrice = Double.parseDouble(editPrice);
                    int editItemID = Integer.parseInt(editId);
                    
                    int int_category = 0;

                        switch (editCategory) { //switch to set category on display
                            case "kitchen":
                                int_category = 1;
                                break;
                            case "bathroom":
                                int_category = 2;
                                break;
                            case "living room":
                                int_category = 3;
                                break;
                            case "basement":
                                int_category = 4;
                                break;
                            case "bedroom":
                                int_category = 5;
                                break;
                            case "garage":
                                int_category = 6;
                                break;
                            case "office":
                                int_category = 7;
                                break;
                            case "utility room":
                                int_category = 8;
                                break;
                            case "storage":
                                int_category = 9;
                                break;
                            case "other":
                                int_category = 10;
                                break;
                            default:
                                int_category = 10;
                                break;
                        }
                    

                    try {
                        is.update(editItemID, int_category, editName, doub_editPrice, editOwner); //inserts user into db table
                        List<Item> itemsList = is.get(username); //reloads updated table from db
                        request.setAttribute("items", itemsList);
                        user = as.get(username);
                        request.setAttribute("user", user);
                        request.setAttribute("message", "Item updated!");

                    } catch (Exception ex) {
                        try {
                            List<Item> usersList = is.get(username); //if there is an error that doesnt allow an insert table is reloaded
                            request.setAttribute("items", usersList);
                            request.setAttribute("message", "error");
                        } catch (Exception ex1) {

                        }
                    }
                }

            } else {
                request.setAttribute("username", username); //sets username to display on web page
                request.setAttribute("usercategory", categoryName);//setting values of input boxes
                request.setAttribute("userItem", itemName);
                request.setAttribute("userPrice", itemPrice);
                List<Item> usersList = is.get(username); //if there is an error that doesnt allow an insert table is reloaded
                request.setAttribute("items", usersList);

                request.setAttribute("error", "Invalid. Please re-enter");//sets error message

                //display form again
                getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response);
                //after reload stop rest of execution
                return;
            }

        } catch (Exception ex) {

            try {
                List<Item> usersList = is.get(username); //if there is an error that doesnt allow an insert table is reloaded
                request.setAttribute("username", username); //sets username to display on web page
                request.setAttribute("items", usersList);
                request.setAttribute("message", "Input cannot be empty:1");

            } catch (Exception ex1) {
                Logger.getLogger(InventoryServlet.class
                .getName()).log(Level.SEVERE, null, ex1);
                request.setAttribute("message", "error");
            }
        }

        request.setAttribute("username", username);
        getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response); //loads inventory page
        return;
    }
}
