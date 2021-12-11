package servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.User;
import services.AccountService;

public class RegistrationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        getServletContext().getRequestDispatcher("/WEB-INF/registration.jsp").forward(request, response); //loads login page
        return;

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        AccountService as = new AccountService();

        String userAction = request.getParameter("userAction");

        if (userAction != null) {

            if (userAction.equals("regUser")) {
                try {

                    String email = request.getParameter("reg_email"); //retrieves values from input boxes
                    String password = request.getParameter("reg_password");                    
                    String fName = request.getParameter("reg_fName");
                    String lName = request.getParameter("reg_lName");
                    boolean isActive = true;
                    int userRole = 2;

                    if (email.length() <= 40 && fName != null && !fName.equals("") && fName.length() <= 20 && lName != null && !lName.equals("") && lName.length() <= 20 && password != null && !password.equals("") && password.length() <= 20) {
                        //if stmt checks to make sure boxes are not empty and that the name/email meets db constraints

                        try {
                            as.insert(email, isActive, password, fName, lName, userRole); //inserts user into db table
                            List<User> usersList = as.getAll(); //reloads updated table from db
                            request.setAttribute("users", usersList);
                            request.setAttribute("message", "User Registered!");

                        } catch (Exception ex) {
                            try {
                                request.setAttribute("message", "error");
                            } catch (Exception ex1) {

                            }
                        }
                    } else {
                        List<User> usersList = as.getAll(); //if there is an error that doesnt allow an insert table is reloaded
                        request.setAttribute("users", usersList);
                        request.setAttribute("message", "There was an error while registering the user.");
                    }
                } catch (Exception ex) {

                }
            }

        }

        getServletContext().getRequestDispatcher("/WEB-INF/registration.jsp").forward(request, response); //loads login page
        return;
    }

}
