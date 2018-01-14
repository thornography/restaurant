package com.gencprogramcilar.core;

import java.sql.*;

public class DatabaseConnection {

    Connection connection;

    public DatabaseConnection()
    {
        openConnection();
    }

    private void openConnection()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            String conStr="jdbc:mysql://localhost:3306/yemeksepeti";
            String userName="root";
            String passw="4401164";
            connection= DriverManager.getConnection(conStr,userName,passw);
        }catch(Exception ex){ex.printStackTrace();}
    }

    public ResultSet getResultSet(String sql, Object... values)
    {
        if(connection==null)
            openConnection();
        ResultSet resultSet;
        try
        {
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            if(values!=null) {
                for (int i = 0; i < values.length; i++) {
                    preparedStatement.setObject(i + 1, values[i]);
                }

            }
            resultSet = preparedStatement.executeQuery();
            return resultSet;

        } catch (Exception ex) {ex.printStackTrace();}

        return null;
    }

    public int getUpdate(String sql,Object... values)
    {
        int affectedRows = 0;
        if(connection==null) openConnection();
        try
        {
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            for(int i=0;i<values.length;i++)
            {
                preparedStatement.setObject(i+1,values[i]);
            }
            affectedRows = preparedStatement.executeUpdate();

        }catch (Exception ex) {ex.printStackTrace();}
        finally {closeConnection();}
        return affectedRows;
    }

    public ResultSet getResultSetNoInput(String sql){
        if(connection==null)
            openConnection();
        try
        {
            Statement statement = this.connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            return rs;

        } catch (Exception ex) {ex.printStackTrace();}

        return null;
    }

    public void closeConnection()
    {
        try{connection.close();}
        catch (Exception ex){ex.printStackTrace();}
    }


}
