package com.gencprogramcilar.controller;

import com.gencprogramcilar.model.Restaurant;
import com.gencprogramcilar.service.RestaurantService;

import java.sql.SQLException;
import java.util.List;

public class RestaurantController {

    public RestaurantController() {
    }

    RestaurantService service = new RestaurantService();

    public void add(String name ,String email,String password,String address,String phone){
        Restaurant restaurant = new Restaurant();
        restaurant.setName(name);
        restaurant.setEmail(email);
        restaurant.setPassword(password);
        restaurant.setAddress(address);
        restaurant.setPhone(phone);
        service.add(restaurant);

    }

    public void edit(String name ,String email,String password,String address,String phone){
        Restaurant restaurant = new Restaurant();
        restaurant.setName(name);
        restaurant.setEmail(email);
        restaurant.setPassword(password);
        restaurant.setAddress(address);
        restaurant.setPhone(phone);
        service.edit(restaurant);

    }

    public void delete(int id){
        service.delete(id);
    }

    public List<Restaurant> get() throws SQLException {
        List<Restaurant> restaurantList=service.get();
        return restaurantList;
    }
}
