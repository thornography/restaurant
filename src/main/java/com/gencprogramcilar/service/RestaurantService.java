package com.gencprogramcilar.service;

import com.gencprogramcilar.core.DatabaseConnection;
import com.gencprogramcilar.model.Customer;
import com.gencprogramcilar.model.CustomerOrder;
import com.gencprogramcilar.model.Food;
import com.gencprogramcilar.model.Restaurant;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RestaurantService {

    public RestaurantService() {
    }

    public void add(Restaurant restaurant) {
        DatabaseConnection connection = new DatabaseConnection();
        String sql = "insert into restaurant(email,name,password,address,phone) values(?,?,?,?,?)";
        Object[] object= new Object[]{restaurant.getEmail(),restaurant.getName(),restaurant.getPassword(),restaurant.getAddress(),restaurant.getPhone()};
        connection.getUpdate(sql,object);
        connection.closeConnection();
    }

    public void edit(Restaurant restaurant) {
        DatabaseConnection connection = new DatabaseConnection();
        String sql = "UPDATE restaurant SET email=?, name=?,password=?, address=?,phone=? WHERE id= ?";
        Object[] object= new Object[]{restaurant.getEmail(),restaurant.getName(),restaurant.getPassword(),restaurant.getAddress(),restaurant.getPhone(),restaurant.getId()};
        connection.getUpdate(sql,object);
        connection.closeConnection();
    }

    public void delete(int id) {
        DatabaseConnection connection = new DatabaseConnection();
        String sql = "delete from restaurant where id = ?";
        Object[] object= new Object[]{id};
        connection.getUpdate(sql,object);
        connection.closeConnection();
    }

    public List<Restaurant> get()  {
        try
        {
            DatabaseConnection connection = new DatabaseConnection();
            String sql = "select * from restaurant";
            ResultSet rs = connection.getResultSet(sql,null);
            List<Restaurant> list = new ArrayList<>();
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String address = rs.getString("address");
                String phone = rs.getString("phone");
                Restaurant restaurant = new Restaurant(id,email,name,password,address,phone);
                list.add(restaurant);
            }
            connection.closeConnection();
            return list;
        }catch (Exception ex){ex.printStackTrace();}
        return null;
    }

    private boolean emailControl(String email) throws SQLException {
        DatabaseConnection connection = new DatabaseConnection();
        String sql = "select * from restaurant where email=?";
        Object[] object=new Object[]{email};
        ResultSet rs = connection.getResultSet(sql,object);
        int id = 0;
        while (rs.next()){
            id = rs.getInt("id");
        }
        connection.closeConnection();
        if (id != 0)
            return true;
        else
            return false;
    }


    public List<CustomerOrder> getOrders(Restaurant restaurant) {
        try
        {
            DatabaseConnection connection = new DatabaseConnection();
            String sql = "select * from customer_order inner join food on food.id=customer_order.id_food inner join restaurant on restaurant.id = food.id_restaurant inner join customer on customer.id = customer_order.id_customer where restaurant.id =? and customer_order.status=0";
            Object[] object=new Object[]{restaurant.getId()};
            ResultSet rs = connection.getResultSet(sql,object);
            List<CustomerOrder> list = new ArrayList<>();
            while(rs.next()){
                int id = rs.getInt("customer_order.id");
                String name = rs.getString("customer.name");
                String surname = rs.getString("customer.surname");
                String address = rs.getString("customer.address");
                Customer customer = new Customer();
                customer.setName(name);
                customer.setSurname(surname);
                customer.setAddress(address);
                String food = rs.getString("food.name");
                Food food1 = new Food();
                food1.setName(food);
                Date date = rs.getDate("customer_order.order_date");
                boolean status = rs.getBoolean("customer_order.status");
                CustomerOrder customerOrder = new CustomerOrder(id,customer,food1,date,status);
                list.add(customerOrder);
            }
            connection.closeConnection();
            return list;
        }catch (Exception ex){ex.printStackTrace();}
        return null;
    }

    public void confirmOrder(CustomerOrder customerOrder){
        DatabaseConnection connection = new DatabaseConnection();
        String sql = "UPDATE customer_order set status=1 where id =?";
        connection.getUpdate(sql,customerOrder.getId());
        connection.closeConnection();
    }

}
