package ru.mta.lotun.javaEE;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Alex on 31.05.2017.
 */
public class Start {
    static DBConnect db = new DBConnect();
    public static void main(String[] args) {
        //запросы
        try {
            Statement star = db.getConnect().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Заполняем базу(WriteDB)
        WriteDB.PreviewData();//Метод для извлечение стартовых данных
        WriteDB.WriteDataBase(); //Заполняем данные в базу

        //Запуск Outpost
        Outpost.UpD();//поменяем пароль у пользователей petr и misha
        Outpost.OutP();//вывод на экран данных по убыванию
        Outpost.Del();//удалим сотрудников зарплата которых менее 50 тыс, их 3е

        Semple.PrintTop();//выводим самого богатого работника
    }

}
