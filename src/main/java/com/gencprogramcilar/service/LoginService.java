package com.gencprogramcilar.service;

import com.gencprogramcilar.core.DatabaseConnection;
import com.gencprogramcilar.model.Admin;
import com.gencprogramcilar.model.Customer;
import com.gencprogramcilar.model.Restaurant;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginService {
    public LoginService() {
    }

    public Object login(String name1,String password1, int type) throws SQLException {
        DatabaseConnection connection = new DatabaseConnection();
        try {
            if (type == 0) {

                String sql = "select * from admin where user_name=? and password = ?;";
                Object[] object = new Object[]{name1,password1};
                ResultSet rs = connection.getResultSet(sql, object);
                Admin admin1 = null;
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("user_name");
                    String password = rs.getString("password");

                    admin1 = new Admin(id, name, password);

                    return admin1;
                }
            } else if (type == 1) {
                String sql = "select * from customer where email=? and password = ?;";
                Object[] object = new Object[]{name1,password1};
                ResultSet rs = connection.getResultSet(sql, object);
                Customer customer1 = null;
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String surname = rs.getString("surname");
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    String address = rs.getString("address");
                    String phone = rs.getString("phone");
                    customer1 = new Customer(id, name, surname, email, password, address, phone);

                    return customer1;
                }
            } else {
                String sql = "select * from restaurant where email=? and password = ?;";
                Object[] object = new Object[]{name1,password1};
                ResultSet rs = connection.getResultSet(sql, object);
                Restaurant restaurant1 = null;
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    String address = rs.getString("address");
                    String phone = rs.getString("phone");
                    restaurant1 = new Restaurant(id, email, name, password, address, phone);
                    return restaurant1;
                }
            }

        } catch (Exception e) {

        }finally {
            connection.closeConnection();
        }
        return null;
    }
}
