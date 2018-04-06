package kr.ac.jejunu;

import org.springframework.beans.factory.annotation.Value;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JejuConnectionMaker implements ConnectionMaker {

    //디버그화살표 옆에 스피너 누르고 edit configuration 누르고
    //envirenment variable 누르고 밑에 밸류 만듬
    //대문자를 소문자로 인식하고 _를 .으로 인식함
    //ex) DB_CLASSNAME 이라고 만듬
    @Value("${db.className}")
    private String className;
    @Value("{db.url}")
    private String url;
    @Value("{db.username}")
    private String userName;
    @Value("{db.password}")
    private String password;

    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        className = "com.mysql.jdbc.Driver";
        Class.forName(className);

        return DriverManager.getConnection(url, userName, password);
    }
}
