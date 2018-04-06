package kr.ac.jejunu;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is; // static 이 붙으면 해당 패키지에 static 메서드를 바로사용가능
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserDaoTest {
    private UserDao userDao;
    private DaoFactory daoFactory;

    @Before
    public void setup(){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(DaoFactory.class);
        userDao = applicationContext.getBean("userDao", UserDao.class);
    }

    @Test
    public void get() throws SQLException, ClassNotFoundException {
        int id= 1;
        User user = userDao.get(id);

        assertThat(user.getId(), is(1));
        assertThat(user.getName(), is("오승열"));
        assertThat(user.getPassword(), is("1234"));
    }

    @Test
    public void add() throws SQLException, ClassNotFoundException {
        User user = new User();
        Integer id = insertUserTest(user);

        User insertedUser = userDao.get(id);
        assertThat(insertedUser.getId(), is(id));
        assertThat(insertedUser.getName(), is(user.getName()));
        assertThat(insertedUser.getPassword(), is(user.getPassword()));
    }

    @Test
    public void update() throws SQLException, ClassNotFoundException {
        User user = new User();
        Integer id = insertUserTest(user);

        user.setId(id);
        user.setName("허윤호");
        user.setPassword("4321");
        userDao.update(user);

        User updatedUser = userDao.get(id);
        assertThat(updatedUser.getId(), is(user.getId()));
        assertThat(updatedUser.getName(), is(user.getName()));
        assertThat(updatedUser.getPassword(), is(user.getPassword()));

    }

    @Test
    public void delete() throws SQLException, ClassNotFoundException {
        User user = new User();
        Integer id = insertUserTest(user);

        userDao.delete(id);

        User deletedUser = userDao.get(id);
        assertThat(deletedUser, nullValue());
    }

    private Integer insertUserTest(User user) throws ClassNotFoundException, SQLException {
        user.setName("헐크");
        user.setPassword("1234");

        return userDao.insert(user);
    }

}
