package com.gencprogramcilar.service;

import com.gencprogramcilar.core.DatabaseConnection;
import com.gencprogramcilar.model.Category;
import com.gencprogramcilar.model.Food;
import com.gencprogramcilar.model.Restaurant;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FoodService {
    public FoodService() {
    }
    public void add(Food food) {
        DatabaseConnection connection = new DatabaseConnection();
        String sql = "insert into food(name,id_category,id_restaurant,price,picture) values(?,?,?,?,?)";
        Object[] object= new Object[]{food.getName(),food.getCategory().getId(),food.getRestaurant().getId(),food.getPrice(),food.getPicture()};
        connection.getUpdate(sql,object);
        connection.closeConnection();
    }

    public void edit(Food food) {
        DatabaseConnection connection = new DatabaseConnection();
        String sql = "UPDATE food SET name = ?,price=? WHERE id= ?";
        Object[] object= new Object[]{food.getName(),food.getPrice(),food.getId()};
        connection.getUpdate(sql,object);
        connection.closeConnection();
    }

    public void delete(int id) {
        DatabaseConnection connection = new DatabaseConnection();
        String sql = "delete from food where id = ?";
        Object[] object= new Object[]{id};
        connection.getUpdate(sql,object);
        connection.closeConnection();
    }

    public List<Food> get(Restaurant restaurant) {
        try
        {
            DatabaseConnection connection = new DatabaseConnection();
            String sql = "select * from food inner join category on food.id_category = category.id where id_restaurant=?";
            ResultSet rs = connection.getResultSet(sql,restaurant.getId());
            List<Food> list = new ArrayList<>();
            while (rs.next()){
                int id = rs.getInt("food.id");
                String name = rs.getString("food.name");
                int categoryId = rs.getInt("category.id");
                String categoryName = rs.getString("category.name");
                Category category = new Category(categoryId,categoryName);
                float price = rs.getFloat("food.price");
                byte[] picture = rs.getBytes("food.picture");
                Food food = new Food(id,restaurant,category,name,price,picture);
                list.add(food);
            }
            connection.closeConnection();
            return list;
        }catch (Exception ex){ex.printStackTrace();}
        return null;
    }

    public List<Food> get(int restaurantId,int categoryId)
    {
        DatabaseConnection connection= new DatabaseConnection();
        String sql="select * from food where id_restaurant=? and id_category=?";
        ResultSet resultSet=connection.getResultSet(sql,restaurantId,categoryId);
        List<Food> foodList=new ArrayList<>();
        try
        {
            while(resultSet.next())
            {
                int id=resultSet.getInt(1);
                int id_restaurant=resultSet.getInt(2);
                int id_category=resultSet.getInt(3);
                String name=resultSet.getString(4);
                float price=resultSet.getFloat(5);
                byte[] picture=resultSet.getBytes(6);
                Food food=new Food(id,id_restaurant,id_category
                        ,name,price,picture);

                foodList.add(food);
            }

        }catch (Exception ex){ex.printStackTrace();}

        return foodList;
    }

}
