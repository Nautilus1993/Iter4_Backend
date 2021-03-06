package com.team16.project.MyList.WishList;

import com.team16.project.core.Bootstrap;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
* This class is responsible for removing wish and listing all wishes of a user
* @author  Team 16
*/
public class WishService {
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;
    private JSONParser parser;

    public WishService() {
        connection = null;
        statement = null;
        resultSet = null;
        parser = new JSONParser();
    }

    // This method removes a single wish from the database
    public int removeWish(String body) throws ParseException {
        JSONObject jsonObject = (JSONObject) parser.parse(body);
        String userId = (String) jsonObject.get("userId");
        String itemId = (String) jsonObject.get("itemId");

        String query = "DELETE FROM WishList " + "WHERE userId = ?" + " AND itemId = ?";
        try {
            connection =  DriverManager.getConnection(Bootstrap.DATABASE);
            statement = connection.prepareStatement(query);
            statement.setString(1, userId);
            statement.setString(2, itemId);
            statement.executeUpdate();
            statement.close();
            connection.close();
            System.out.print("Successfully removed wish");
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    // This mehtod lists all wishes of a particular user.
    public List<Wish> listWishes(String body)  {
        String query = "SELECT * " + "FROM WishList NATURAL JOIN Item NATURAL JOIN UserDetail " + "WHERE WishList.userId = ?";
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) parser.parse(body);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String userID =  (String) jsonObject.get("toBeVerified");

        try {
            connection =  DriverManager.getConnection(Bootstrap.DATABASE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            statement = connection.prepareStatement(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            statement.setString(1, userID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<Wish> wishes = new ArrayList<Wish>();

        try {
            while (resultSet.next()) {
                wishes.add(new Wish(resultSet.getString("name"), resultSet.getString("imgPath"),
                        resultSet.getString("condition"), resultSet.getString("price"), resultSet.getString("avialableDate"), resultSet.getString("expireDate"), resultSet.getString("itemId")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            postQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return wishes;
    }

    private void postQuery() throws SQLException {
        try {
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
