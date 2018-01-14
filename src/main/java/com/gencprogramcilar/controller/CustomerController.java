package com.gencprogramcilar.controller;

import com.gencprogramcilar.model.Customer;
import com.gencprogramcilar.service.CustomerService;

import java.sql.SQLException;
import java.util.List;

public class CustomerController {

    public CustomerController() {
    }

    CustomerService service = new CustomerService();

    public void add(String name ,String email,String password,String address,String phone){
        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setPassword(password);
        customer.setAddress(address);
        customer.setPhone(phone);
        service.add(customer);

    }

    public void edit(String name ,String surname,String email,String password,String address,String phone){
        Customer customer = new Customer();
        customer.setName(name);
        customer.setSurname(surname);
        customer.setEmail(email);
        customer.setPassword(password);
        customer.setAddress(address);
        customer.setPhone(phone);
        service.edit(customer);

    }

    public void delete(int id){
        service.delete(id);
    }

    public List<Customer> get() throws SQLException {
        List<Customer> list=service.get();
        return list;
    }

}
