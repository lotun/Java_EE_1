package ru.mta.lotun.javaEE;

import com.sun.org.glassfish.gmbal.Description;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Alex on 23.05.2017.
 */
public class Outpost {
    static DBConnect db = new DBConnect() ;


    public Outpost() throws SQLException {
    }


    public static void Del(){
        Statement star = null;

        //удаление 3х строк
        //удалим сотрудников зарплата которых менее 50 тыс, их 3е
        try {
            star = db.getConnect().createStatement();
            int y=star.executeUpdate("DELETE FROM staff WHERE paycheck<=50000");
            System.out.println("Удалено записей: " + y);
            star.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @Description("Обновление")
    public static void UpD(){
        //вводим изменение
        //поменяем пароль у пользователей petr и misha
        //вначале сгенирируем два пароля, пусть только из цифр
        int p1 = 1000000 + (int) (Math.random() * 9000000);//случайный  пароль
        int p2 = 1000000 + (int) (Math.random() * 9000000);//случайный пароль
        //захэшируем пароль
        String hP1 = WriteDB.md5Custom(String.valueOf(p1));
        String hP2 = WriteDB.md5Custom(String.valueOf(p2));

        Statement star = null;

        try {
            star = db.getConnect().createStatement();
            if(star.executeUpdate("UPDATE staff SET password='"+hP1+"'  WHERE  login = 'petr'")>0)
                System.out.println("Пользователю petr пароль успешно изменен");

            if(star.executeUpdate("UPDATE staff SET password='"+hP2+"'  WHERE  login = 'misha'")>0)
                System.out.println("Пользователю misha пароль успешно изменен");
        star.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println();
    }

    public static void OutP(){
        Statement star = null;

        //вывод на экран данных по убыванию
        try {
            star = db.getConnect().createStatement();
            ResultSet rs = star.executeQuery("Select * from staff join post on staff.pos_id=post.pos_id order by id desc");
            while (rs.next()){
                int id =rs.getInt("id");
                String name = rs.getString("name");
                String lname = rs.getString("lname");
                String post = rs.getString("position");
                int paycheck = rs.getInt("paycheck");
                String login  = rs.getString("login");

                System.out.println(id + " | " + name + " | " + lname + " | " + post + " | " + paycheck + " | " + login);
            }
            star.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
