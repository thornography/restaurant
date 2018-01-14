package com.gencprogramcilar.service;

import com.gencprogramcilar.core.DatabaseConnection;
import com.gencprogramcilar.model.Category;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryService {
    public CategoryService() {
    }

    public void add(Category category) {
        DatabaseConnection connection = new DatabaseConnection();
        String sql = "insert into category(name) values(?)";
        Object[] object= new Object[]{category.getName()};
        connection.getUpdate(sql,object);
        connection.closeConnection();
    }

    public void edit(Category category) {
        DatabaseConnection connection = new DatabaseConnection();
        String sql = "UPDATE category SET name = ? WHERE id= ?";
        Object[] object= new Object[]{category.getName(),category.getId()};
        connection.getUpdate(sql,object);
        connection.closeConnection();
    }

    public void delete(int id) {
        DatabaseConnection connection = new DatabaseConnection();
        String sql = "delete from category where id = ?";
        Object[] object= new Object[]{id};
        connection.getUpdate(sql,object);
        connection.closeConnection();
    }

    public List<Category> get() throws SQLException {
        DatabaseConnection connection = new DatabaseConnection();
        String sql = "select * from category";
        ResultSet rs = connection.getResultSetNoInput(sql);
        List<Category> list = new ArrayList<>();
        while (rs.next()){
            int id = rs.getInt("id");
            String name = rs.getString("name");
            Category category = new Category(id,name);
            list.add(category);
        }
        connection.closeConnection();
        return list;
    }

}
