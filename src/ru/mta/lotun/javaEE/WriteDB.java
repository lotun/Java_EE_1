package ru.mta.lotun.javaEE;

import com.sun.org.glassfish.gmbal.Description;
import ru.mta.lotun.javaEE.DBConnect;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.Iterator;

import static java.lang.Math.abs;

/**
 * Created by Alex on 19.05.2017.
 */
public class WriteDB {
    static DBConnect db = new DBConnect() ;

    static List mass = new ArrayList();  //коллекция сотрудников
    static Map<Integer,String> profList = new HashMap<>();  //коллекция профессий


    public WriteDB() throws SQLException {
    }

    public static void main(String[] args) {
        //System.out.println(db.getConnect());
        //запросы
        try {
            Statement star = db.getConnect().createStatement();
//            ResultSet rs = star.executeQuery("Select * from staff");
//
//            File file = new File("result.txt");
//            PrintWriter pw = new PrintWriter(file);
//
//            while (rs.next()){
//                String name = rs.getString("name");
//                String lname = rs.getString("lname");
//                int paycheck = rs.getInt("paycheck");
//
//                pw.println(name + " | " + lname + " | " + paycheck);
//            }
//            pw.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } //catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        PreviewData();//Метод для извлечение стартовых данных
        WriteDataBase(); //Заполняем данные в базу
    }
    @Description("Метод для извлечение стартовых данных")
    private static void   PreviewData(){//int dataType=0

        BufferedReader fr = null; //объект файл
        String line; //каждая строка файла

        try {
             fr =  new BufferedReader(new FileReader("sourse.csv"));

                while ((line = fr.readLine()) != null) {
                    ArrayList newUser = new ArrayList(); // коллекция коллег
                    String[] arrL = line.split(";");// массив для функции split

                    for (int i=0;i<arrL.length;i++){

                        if (i==2){ //если мы попали на поле профессии, сразу заносим профессии в отдельную коллекцию,
                            // а в текущую мы добавим его хэшкод
                            profList.put(abs(arrL[i].hashCode()),arrL[i]);
                            newUser.add(abs(arrL[i].hashCode()));
                            continue;
                        }
                        if(i==5){//сли мы добавляем пароль, проведем его через хэш
                            newUser.add(md5Custom(arrL[i]));
                            continue;
                        }
                        newUser.add(arrL[i]);
                    }
                    mass.add(newUser);

                }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Description("Метод добавление данных в базу")
    private static void WriteDataBase(){
        System.out.println("метод");
        try {
            Statement star = db.getConnect().createStatement();

            //добавить данные в таблицу профессий
            for ( Map.Entry<Integer, String> pw: profList.entrySet()){
                star.executeUpdate("insert into post(pos_id,position) values("+pw.getKey()+",'"+pw.getValue()+"')");
            }
            //добавить данные в таблицу пользователей
            boolean flag=true;
            for (Iterator<ArrayList> iter = mass.iterator();iter.hasNext();){
                //первое поля содержет имена полей, пропускаем
                if (flag){flag = false;iter.next();}

                ArrayList w  = iter.next();
                //несение записи в базу
                star.executeUpdate("insert into staff(name,lname,pos_id,paycheck,login,password) values('"+w.get(0)+"','"+w.get(1)+"',"+w.get(2)+","+w.get(3)+",'"+w.get(4)+"','"+w.get(5)+"')");         //("Select * from staff");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Description("Создание 128 битного хэша")
    public static String md5Custom(String st) {
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(st.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            // тут можно обработать ошибку
            // возникает она если в передаваемый алгоритм в getInstance(,,,) не существует
            e.printStackTrace();
        }

        BigInteger bigInt = new BigInteger(1, digest);
        String md5Hex = bigInt.toString(16);

        while( md5Hex.length() < 32 ){
            md5Hex = "0" + md5Hex;
        }

        return md5Hex;
    }
}
