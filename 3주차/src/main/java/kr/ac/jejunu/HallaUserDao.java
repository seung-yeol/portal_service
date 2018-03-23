package kr.ac.jejunu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//UserDao를 상속한 Template Method Pattern을 적용
public class HallaUserDao extends UserDao{

    //JejuUserDao랑 다르게 구현했다고 가정
    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");

        return DriverManager.getConnection("jdbc:mysql://localhost/jeju?characterEncoding=utf-8"
                , "root", "1234");
    }
}
