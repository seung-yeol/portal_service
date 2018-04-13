package kr.ac.jejunu;

import java.sql.*;

public class UserDao {
    private final JdbcContext jdbcContext;
//    private final ConnectionMaker connectionMaker; 얘를 밑에걸로 바꿔줌

    public UserDao(JdbcContext jdbcContext) {
        this.jdbcContext = jdbcContext;
    }

    public User get(int id) throws SQLException {
        String sql = "select * from userinfo where id = ?";
        Object[] params = new Object[]{id};
        return jdbcContext.queryForObject(sql, params);
    }

    public Integer insert(User user) throws SQLException {
        String sql = "insert into userinfo(name, password) values (?, ?)";
        Object[] params = new Object[]{user.getName(), user.getPassword()};

        return jdbcContext.insert(sql, params);
    }

    public void update(User user) throws SQLException {
        String sql = "update userinfo set name = ?, password = ? where id = ?";
        Object[] params = new Object[]{user.getName(), user.getPassword(), user.getId()};

        jdbcContext.update(sql, params);
    }



    public void delete(Integer id) throws SQLException {
        String sql = "delete from userinfo where id = ?";
        Object[] params = new Object[]{id};

        jdbcContext.update(sql, params);
    }
}