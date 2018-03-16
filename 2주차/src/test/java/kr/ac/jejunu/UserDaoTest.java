package kr.ac.jejunu;

import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.is; // static 이 붙으면 해당 패키지에 static 메서드를 바로사용가능
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserDaoTest {
    private UserDao userDao;

    //before 하면 테스트 실행전에 무조건 실행됨
    @Before
    public void setup(){
        userDao = new UserDao();
    }

    @Test
    //Exception처리는 확실히 처리할 수 있을때 catch해내어야함.
    public void get() throws SQLException, ClassNotFoundException {
        int id= 1;
        User user = userDao.get(id);

        //assertThat 은 비교하는 용으로 사용
        assertThat(user.getId(), is(1));
        assertThat(user.getName(), is("오승열"));
        assertThat(user.getPassword(), is("1234"));
    }

    @Test
    public void add() throws SQLException, ClassNotFoundException {
        User user = new User();
        user.setName("헐크");
        user.setPassword("1234");

        //return값 없어도 되지만, 테스트를 하기위해 받아옴
        Integer id = userDao.insert(user);

        User insertedUser = userDao.get(id);
        assertThat(insertedUser.getId(), is(id));
        assertThat(insertedUser.getName(), is(user.getName()));
        assertThat(insertedUser.getPassword(), is(user.getPassword()));
    }
}
