package services;

import dataaccess.UserDB;
import java.util.HashMap;
import java.util.UUID;
import models.User;

public class EmailService {

    public User login(String email, String password, String path) {
        UserDB userDB = new UserDB();

        try {
            User user = userDB.get(email);
            if (password.equals(user.getPassword())) {

                String to = user.getEmail();
                String subject = "Notes App Login";
                String template = path + "/emailtemplates/login.html";

                HashMap<String, String> tags = new HashMap<>();
                tags.put("firstname", user.getFirstName());
                tags.put("lastname", user.getLastName());
                tags.put("date", (new java.util.Date()).toString());

                GmailService.sendMail(to, subject, template, tags);

                return user;
            }
        } catch (Exception e) {

        }

        return null;
    }

    public void resetPassword(String email, String path, String url) {
        try {
            UserDB userDB = new UserDB();
            User user = userDB.get(email);

            String uuid = UUID.randomUUID().toString();
            String link = url + "?uuid=" + uuid;

            user.setResetPasswordUuid(uuid);

            userDB.update(user);

            //send mail
            String recipient = user.getEmail();
            String subject = "Password Reset";
            String template = path + "/emailtemplates/resetpassword.html";

            HashMap<String, String> tags = new HashMap<>();
            tags.put("firstname", user.getFirstName());
            tags.put("lastname", user.getLastName());
            tags.put("link", link);

            GmailService.sendMail(recipient, subject, template, tags);

        } catch (Exception ex) {

        }
    }

    public boolean changePassword(String uuid, String password) {
        UserDB userDB = new UserDB();
        try {
            User user = userDB.getByUUID(uuid);
            user.setPassword(password);
            user.setResetPasswordUuid(null);
            userDB.update(user);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public void activateAccount(String email, String path, String url) {
        try {
            UserDB userDB = new UserDB();
            User user = userDB.get(email);

            String uuid = UUID.randomUUID().toString();
            String link = url + "?uuid=" + uuid;

            user.setResetPasswordUuid(uuid);

            userDB.update(user);

            //send mail
            String recipient = user.getEmail();
            String subject = "Activation";
            String template = path + "/emailtemplates/activation.html";

            HashMap<String, String> tags = new HashMap<>();
            tags.put("firstname", user.getFirstName());
            tags.put("lastname", user.getLastName());
            tags.put("link", link);

            GmailService.sendMail(recipient, subject, template, tags);

        } catch (Exception ex) {

        }
    }
    
     public boolean newAccountActivation(String uuid, String password) {
        UserDB userDB = new UserDB();
        try {
            User user = userDB.getByUUID(uuid);
            user.setActiveStatus(Boolean.TRUE);
            user.setResetPasswordUuid(null);
            userDB.update(user);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
