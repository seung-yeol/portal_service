package kr.ac.jejunu;

import lombok.AllArgsConstructor;
import lombok.Cleanup;

import javax.sql.DataSource;
import java.sql.*;

@AllArgsConstructor
public class JdbcContext {
    private DataSource dataSource;

    public User jdbcContextForGet(StatementStrategy statementStrategy) throws SQLException {
        User user = null;

        //Cleanup을 하면
        @Cleanup
        Connection connection = dataSource.getConnection();
        @Cleanup
        PreparedStatement preparedStatement = statementStrategy.makeStatement(connection);
        @Cleanup
        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next()) {
            user = new User();
            user.setId(resultSet.getInt("id"));
            user.setName(resultSet.getString("name"));
            user.setPassword(resultSet.getString("password"));
        }

        return user;
    }
    public void jdbcContextForUpdate(StatementStrategy statementStrategy) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = statementStrategy.makeStatement(connection);

            preparedStatement.executeUpdate();

        } finally {
            if (preparedStatement != null)
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if (connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

        }
    }
    public Integer jdbcContextForInsert(StatementStrategy statementStrategy) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Integer id;
        try {
            connection = dataSource.getConnection();
            preparedStatement = statementStrategy.makeStatement(connection);

            preparedStatement.executeUpdate();

//            preparedStatement = connection.prepareStatement("select last_insert_id()");
            //Statement.RETURN_GENERATED_KEYS를 InsertUserStatementStrategy에 매개변수로 추가로 넣어서 getGeneratedKeys()로 받아옴
            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();

            id = resultSet.getInt(1);
        } finally {
            if (resultSet != null)
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if (preparedStatement != null)
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if (connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

        }
        return id;
    }

    public void update(String sql, Object[] params) throws SQLException {
        StatementStrategy statementStrategy = connection ->  {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for(int i = 0 ; i < params.length ; i++){
                preparedStatement.setObject(i+1, params[i]);
            }
            return preparedStatement;
        };

        jdbcContextForUpdate(statementStrategy);
    }

    public Integer insert(String sql, Object[] params) throws SQLException {
        StatementStrategy statementStrategy = connection ->  {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    sql, Statement.RETURN_GENERATED_KEYS);

            for (int i = 0; i < params.length ; i++){
                preparedStatement.setObject(i+1, params[i]);
            }

            return preparedStatement;

        };
        return jdbcContextForInsert(statementStrategy);
    }

    public User queryForObject(String sql, Object[] params) throws SQLException {
        StatementStrategy statementStrategy = connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for (int i = 0 ; i < params.length ; i++){
                preparedStatement.setObject(i + 1, params[i]);
            }
            return preparedStatement;
        };

        return jdbcContextForGet(statementStrategy);
    }
}
