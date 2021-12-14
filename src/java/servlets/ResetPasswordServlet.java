package servlets;

import dataaccess.UserDB;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;
import services.EmailService;

public class ResetPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession session = request.getSession();

        String email = (String) session.getAttribute("email");

        if (email != null) {
            session.setAttribute("email", email);
        }

        if (request.getParameter("uuid") != null) {
            try {
                               
                UserDB uDB = new UserDB();
                User user = uDB.get(email);

                String uuid = request.getParameter("uuid");

                user.setResetPasswordUuid(uuid);

                uDB.update(user);

                request.setAttribute("uuid", uuid);

                getServletContext().getRequestDispatcher("/WEB-INF/resetNewPassword.jsp").forward(request, response);
                return;

            } catch (Exception ex) {
                
            }

        } else {
            
            getServletContext().getRequestDispatcher("/WEB-INF/reset.jsp").forward(request, response);
            return;
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        EmailService es = new EmailService();
        HttpSession session = request.getSession();

        if (request.getParameter("uuid") != null) {
            String uuid = request.getParameter("uuid");

            String newPass = request.getParameter("newPass");

            if (newPass == null || newPass.equals("")) {
                request.setAttribute("message", "Invalid password!");
                getServletContext().getRequestDispatcher("/WEB-INF/resetNewPassword.jsp").forward(request, response);
                return;
            } else {
                es.changePassword(uuid, newPass);
                request.setAttribute("message", "Password Changed!");
                getServletContext().getRequestDispatcher("/WEB-INF/resetNewPassword.jsp").forward(request, response);
                return;

            }

        } else {
            
            
            String path = getServletContext().getRealPath("/WEB-INF");
            String url = request.getRequestURL().toString();

            String email = request.getParameter("email");
            session.setAttribute("email", email);

            if (email == null || email.equals("")) {
                request.setAttribute("message", "Invalid email!");
                getServletContext().getRequestDispatcher("/WEB-INF/reset.jsp").forward(request, response);
                return;
            } else {
                es.resetPassword(email, path, url);
                request.setAttribute("message", "If there is an account registered under this email you will receive an email");
                getServletContext().getRequestDispatcher("/WEB-INF/reset.jsp").forward(request, response);
            }
        }

    }

}
