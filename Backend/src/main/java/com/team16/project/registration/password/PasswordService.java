package com.team16.project.registration.password;

import com.team16.project.Model.PasswordDB;
import org.json.simple.parser.ParseException;

import javax.mail.MessagingException;
import java.sql.SQLException;
/**
* This class is responsible for registering the username and the password when a new user is created
* @author  Team 16
*/
public class PasswordService {
    private PasswordDB passwordDB;

    PasswordService() throws SQLException {
        passwordDB = new PasswordDB();
    }


    int createUser(String body) throws ParseException, SQLException, MessagingException {
        return passwordDB.insertUser(body);
    }
}

