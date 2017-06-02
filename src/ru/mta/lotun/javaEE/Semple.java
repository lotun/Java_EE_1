package ru.mta.lotun.javaEE;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Alex on 23.05.2017.
 */
public class Semple {
    static DBConnect db = new DBConnect() ;


    public static void PrintTop() {
        //выводим самого богатого работника
        Statement star = null;
        try {
            star = db.getConnect().createStatement();
            ResultSet rs = star.executeQuery("Select staff.paycheck, staff.name,staff.lname,staff.login,post.position FROM staff join post on staff.pos_id=post.pos_id ORDER BY staff.paycheck DESC LIMIT 1");
            while (rs.next()){
                String name = rs.getString("name");
                String lname = rs.getString("lname");
                String post = rs.getString("position");
                int paycheck = rs.getInt("paycheck");
                String login  = rs.getString("login");

                System.out.println(name + " | " + lname + " | " + post + " | " + paycheck + " | " + login);
            }
            star.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
