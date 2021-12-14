package servlets;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Category;
import models.User;
import services.AccountService;
import services.CategoryService;

public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        HttpSession session = request.getSession(); //getting session

        String sess_username = (String) session.getAttribute("sessionUser"); //grabing session variable

        AccountService as = new AccountService();
        CategoryService cs = new CategoryService();

        String action = request.getParameter("action");
        String email = request.getParameter("email");

        if (action != null) {
            if (action.equals("editCat")) {
                String catId = request.getParameter("categoryId");
                int int_catId = Integer.parseInt(catId);

                try {
                    Category category = cs.get(int_catId);
                    request.setAttribute("editCatID", catId); //sets all input boxes to email users info
                    request.setAttribute("editCatName", category.getCategory_name());
                    List<Category> categoriesList = cs.getAll(); //reloads table from db
                    request.setAttribute("categories", categoriesList);

                } catch (Exception ex) {

                }
            } else if (email.contains(" ")) {
                String emailArray[] = email.split(" ");
                String emailMake = emailArray[0] + "+" + emailArray[1];
                email = emailMake;
            }

            if (action.equals("edit")) {
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

            if (action.equals("delete")) {
                if (!sess_username.equals(email)) {
                    try {
                        as.delete(email);// if action = deleted users is removed from db by email
                        List<User> usersList = as.getAll(); //reloads table from db
                        request.setAttribute("users", usersList);

                    } catch (Exception ex) {
                        request.setAttribute("message", "Error.");
                    }
                } else {
                    request.setAttribute("message", "Cannot delete self.");
                }

            }
        }

        try {
            List<User> usersList = as.getAll();
            request.setAttribute("users", usersList);

            List<Category> categoriesList = cs.getAll(); //reloads table from db
            request.setAttribute("categories", categoriesList);
        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", "Loading error");
        }

        getServletContext()
        .getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response); //loads login page

        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        AccountService as = new AccountService();
        CategoryService cs = new CategoryService();

        String sess_username = request.getParameter("username");
        String userAction = request.getParameter("userAction");

        if (userAction != null) {
            if (userAction.equals("save")) {

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
                        List<Category> categoriesList;
                        categoriesList = cs.getAll(); //reloads table from db
                        request.setAttribute("categories", categoriesList);
                        request.setAttribute("message", "User Edited!");
                    } else {
                        request.setAttribute("message", "There was an error while saving a user.");
                        try {
                            List<User> usersList1; //if there is an error that doesnt allow an update table is reloaded
                            usersList1 = as.getAll();
                            List<Category> categoriesList;
                            categoriesList = cs.getAll(); //reloads table from db
                            request.setAttribute("categories", categoriesList);
                            request.setAttribute("users", usersList1);
                        } catch (Exception ex) {

                        }
                    }
                } catch (Exception ex) {

                }

            }

            if (userAction.equals("addUser")) {
                try {

                    String password = request.getParameter("password");
                    String email = request.getParameter("email"); //retrieves values from input boxes
                    String fName = request.getParameter("fName");
                    String lName = request.getParameter("lName");
                    boolean isActive = true;
                    int userRole = 1;

                    if (email.length() <= 40 && fName != null && !fName.equals("") && fName.length() <= 20 && lName != null && !lName.equals("") && lName.length() <= 20 && password != null && !password.equals("") && password.length() <= 20) {
                        //if stmt checks to make sure boxes are not empty and that the name/email meets db constraints

                        try {
                            as.insert(email, isActive, password, fName, lName, userRole); //inserts user into db table
                            List<User> usersList = as.getAll(); //reloads updated table from db
                            request.setAttribute("users", usersList);
                            List<Category> categoriesList;
                            categoriesList = cs.getAll(); //reloads table from db
                            request.setAttribute("categories", categoriesList);
                            request.setAttribute("errorMsg", "User Added!");

                        } catch (Exception ex) {
                            try {
                                List<User> usersList = as.getAll(); //if there is an error that doesnt allow an insert table is reloaded
                                request.setAttribute("users", usersList);
                                List<Category> categoriesList;
                                categoriesList = cs.getAll(); //reloads table from db
                                request.setAttribute("categories", categoriesList);
                                request.setAttribute("message", "error");
                            } catch (Exception ex1) {

                            }
                        }
                    } else {
                        List<User> usersList = as.getAll(); //if there is an error that doesnt allow an insert table is reloaded
                        request.setAttribute("users", usersList);
                        List<Category> categoriesList;
                        categoriesList = cs.getAll(); //reloads table from db
                        request.setAttribute("categories", categoriesList);
                        request.setAttribute("message", "There was an error while adding a user.");
                    }
                } catch (Exception ex) {

                }
            } else if (userAction.equals("addCat")) {
                String categoryName = request.getParameter("addCatName"); //retrieves values from input boxes

                try {
                    List<Category> allItems = cs.getAll();
                    int numCats = allItems.size();

                    cs.insert(numCats + 1, categoryName); //inserts user into db table
                    List<Category> categoriesList;
                    categoriesList = cs.getAll(); //reloads table from db
                    request.setAttribute("categories", categoriesList);
                    request.setAttribute("errorMsg", "Category added!");
                } catch (Exception ex) {
                    Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
                    List<Category> categoriesList;
                    try {
                        categoriesList = cs.getAll(); //reloads table from db
                        request.setAttribute("categories", categoriesList);
                        request.setAttribute("errorMsg", "Category Update Error!");
                    } catch (Exception ex1) {
                        Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex1);
                    }

                }

            } else if (userAction.equals("saveCat")) {
                String categoryId = request.getParameter("editCatID");
                int int_categoryId = Integer.parseInt(categoryId);
                String categoryName = request.getParameter("editCatName"); //retrieves values from input boxes

                try {
                    cs.update(int_categoryId, categoryName); //inserts user into db table
                    List<Category> categoriesList;
                    categoriesList = cs.getAll(); //reloads table from db
                    request.setAttribute("categories", categoriesList);
                    request.setAttribute("errorMsg", "Category Updated!");
                } catch (Exception ex) {
                    Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
                    List<Category> categoriesList;
                    try {
                        categoriesList = cs.getAll(); //reloads table from db
                        request.setAttribute("categories", categoriesList);
                        request.setAttribute("errorMsg", "Category Update Error!");
                    } catch (Exception ex1) {
                        Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex1);
                    }

                }

            }
        }

        request.setAttribute("username", sess_username);
        getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response); //loads login page
        return;
    }

}
