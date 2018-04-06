package kr.ac.jejunu;

import java.sql.*;

public class UserDao {
    //strategy pattern 의 context는 변하지 않는 부분.
    private final ConnectionMaker connectionMaker;

    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public User get(int id) throws ClassNotFoundException, SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user;

        //try 안의 작업을 하다가 오류가 생긴경우 서버의 연결을 해제해주어야해서 finally부분으로 close()시켜줌
        try {
            connection = connectionMaker.getConnection();

            preparedStatement = connection.prepareStatement("select * from userinfo where id = ?");
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();

            resultSet.next();
            user = new User();
            user.setId(resultSet.getInt("id"));
            user.setName(resultSet.getString("name"));
            user.setPassword(resultSet.getString("password"));
        } finally {
            if (resultSet != null)
                resultSet.close();
            if (preparedStatement != null)
                //preparedStatement가 만들어 지기도 전에 에러가 생성될수 있으므로
                try {
                    preparedStatement.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            if (connection != null)
                //connection이 만들어 지기도 전에 에러가 생성될수 있으므로
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return user;
    }

    public Integer insert(User user) throws ClassNotFoundException, SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Integer id;

        try {
            connection = connectionMaker.getConnection();

            preparedStatement = connection.prepareStatement(
                    "insert into userinfo(name, password) VALUES(?,?)");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());

            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("select last_insert_id()");
            resultSet = preparedStatement.executeQuery();
            resultSet.next();

            id = resultSet.getInt(1);
        } finally {
            if (resultSet != null)
                resultSet.close();
            if (preparedStatement != null)
                try {
                    preparedStatement.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            if (connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return id;
    }
}
