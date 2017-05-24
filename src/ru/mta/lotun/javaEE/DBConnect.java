package ru.mta.lotun.javaEE;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.*;

/**
 * Created by Alex on 16.05.2017.
 */
public class DBConnect {
/*    public static void main(String[] args) {
        System.out.println(getConnect());
        //запросы
        try {
            Statement star = getConnect().createStatement();
            ResultSet rs = star.executeQuery("Select * from staff");

            File file = new File("result.txt");
            PrintWriter pw = new PrintWriter(file);

            while (rs.next()){
                String name = rs.getString("name");
                String lname = rs.getString("lname");
                int paycheck = rs.getInt("paycheck");

                pw.println(name + " | " + lname + " | " + paycheck);
            }
            pw.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }*/
    void DBConnect(){

    }

    public Connection getConnect() {
        try {
            //DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
            Class.forName("com.mysql.jdbc.Driver").newInstance();//подключение
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/DB-java-ee-1","java","1000");//подключение к базе

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
