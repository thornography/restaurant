package com.gencprogramcilar.model;

import java.io.Serializable;
import java.util.Date;

public class CustomerOrder implements Serializable {
    private int id;
    private Customer customer;
    private Food food;
    private Date orderDate;
    private boolean status;

    public CustomerOrder() {
    }

    public CustomerOrder(int id, Customer customer, Food food, Date orderDate, boolean status) {
        this.id = id;
        this.customer = customer;
        this.food = food;
        this.orderDate = orderDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getFoodName()
    {
        return food.getName();
    }

    public String getCustomerName()
    {
        return customer.getName();
    }
}
