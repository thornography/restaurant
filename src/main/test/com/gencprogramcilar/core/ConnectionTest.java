package com.gencprogramcilar.core;

import org.junit.Test;

import java.sql.ResultSet;

public class ConnectionTest
{
    @Test
    public void nullObjectSqlTest()
    {
        DatabaseConnection connection=new DatabaseConnection();
        String sql="select * from category;";
        ResultSet resulSet=connection.getResultSet(sql,null);
        try
        {
            while(resulSet.next())
            {
                System.out.println(resulSet.getString(2));
            }

        }catch (Exception ex){ex.printStackTrace();}
        connection.closeConnection();
    }
}
