package com.gencprogramcilar.service;

import com.gencprogramcilar.core.DatabaseConnection;
import com.gencprogramcilar.model.Admin;
import com.gencprogramcilar.model.Customer;
import com.gencprogramcilar.model.Restaurant;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerService {
    public CustomerService() {
    }

    public void add(Customer customer) {
        DatabaseConnection connection = new DatabaseConnection();
        String sql = "insert into customer(email,name,surname,password,address,phone) values(?,?,?,?,?,?)";
        Object[] object= new Object[]{customer.getEmail(),customer.getName(),customer.getSurname(),customer.getPassword(),customer.getAddress(),customer.getPhone()};
        connection.getUpdate(sql,object);
    }

    public void edit(Customer customer) {
        DatabaseConnection connection = new DatabaseConnection();
        String sql = "UPDATE restaurant SET email=?, name=?,surname=?,password=?, addresss=?,phone=? WHERE id= ?";
        Object[] object= new Object[]{customer.getEmail(),customer.getName(),customer.getSurname(),customer.getPassword(),customer.getAddress(),customer.getPhone(),customer.getId()};
        connection.getUpdate(sql,object);
    }

    public void delete(int id) {
        DatabaseConnection connection = new DatabaseConnection();
        String sql = "delete from customer where id = ?";
        Object[] object= new Object[]{id};
        connection.getUpdate(sql,object);
    }

    public List<Customer> get() throws SQLException {
        DatabaseConnection connection = new DatabaseConnection();
        String sql = "select * from restaurant";
        ResultSet rs = connection.getResultSet(sql,null);
        List<Customer> list = new ArrayList<>();
        while (rs.next()){
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String surname = rs.getString("surname");
            String email = rs.getString("email");
            String password = rs.getString("password");
            String address = rs.getString("address");
            String phone = rs.getString("phone");
            Customer customer = new Customer(id,email,name,surname,password,address,phone);
            list.add(customer);
        }
        return list;

    }
    


    /**
     * Checks if it is possible to create an account with the given email.
     * if the email exists on the system(customer table) already , it returns false .
     * @param email
     * @return boolean
     */
    public boolean emailControl(String email) {
        try
        {
            DatabaseConnection connection = new DatabaseConnection();
            String sql = "select * from customer where email=?";
            Object[] object=new Object[]{email};
            ResultSet rs = connection.getResultSet(sql,object);
            int id = 0;
            while (rs.next()){
                id = rs.getInt("id");
                System.out.println(id);
            }
            connection.closeConnection();
            if (id != 0)
                return false;
            else
                return true;
        }catch (Exception ex){ex.printStackTrace();}
        return false;
    }
}
