package com.gencprogramcilar.service;

import org.junit.Test;

public class ServiceTest
{
    @Test
    public void emailControlTest()
    {
        CustomerService customerService=new CustomerService();
        try
        {
            boolean check=customerService.emailControl("alicancan");
            System.out.println(check);

        }catch (Exception ex){ex.printStackTrace();}
    }
}
