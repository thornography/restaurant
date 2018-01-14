package com.gencprogramcilar.service;

import com.gencprogramcilar.core.DatabaseConnection;
import com.gencprogramcilar.model.CustomerOrder;

public class CustomerOrderService {

    public CustomerOrderService() {
    }
    public void add(CustomerOrder order) {
        DatabaseConnection connection = new DatabaseConnection();
        String sql = "insert into customer_order(id_customer,id_food,order_date,status) values(?,?,sysdate(),0)";
        Object[] object= new Object[]{order.getCustomer().getId(),order.getFood().getId()};
        connection.getUpdate(sql,object);
        connection.closeConnection();
    }

    public void edit(CustomerOrder order) {

    }

    public void delete(int id) {

    }

    public void getir() {

    }


}
