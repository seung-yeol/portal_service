package com.example.demo;

import static org.hamcrest.CoreMatchers.*;

import static org.hamcrest.MatcherAssert.*;

import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //테스트 할때마다 포트를 랜덤하게 지정해줌
public class UserTest {
	private final String PATH = "/api/user";
	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void get(){
		Integer id = 1;
		String name = "오승열";
		String password = "1234";

		User user = restTemplate.getForObject(PATH + "/" + id, User.class);

		assertThat(user.getId(), is(id));
		assertThat(user.getName(), is(name));
		assertThat(user.getPassword(), is(password));
	}

	@Test
	public void list() {
		List<User> users = restTemplate.getForObject(PATH + "/list", List.class);
		assertThat(users, IsEmptyCollection.empty());
	}

	@Test
	public void create() {
		String name = "오오오";
		String password = "1111";
		User user = createUser(name, password);

		User createUser = restTemplate.postForObject(PATH, user, User.class);

		validate(name, password, createUser);
	}

	@Test
	public void modify(){
		String name = "hulk";
		String password = "1234";
		User createdUser = createUser(name, password);

		//수정
		createdUser.setPassword("1111");

		restTemplate.put(PATH, createdUser);

		validate(name, "1111", createdUser);
	}

	@Test
	public void delete(){
		String name = "오오오";
		String password = "1111";
		User createUser = createUser(name, password);
		validate(name, password, createUser);

		restTemplate.delete(PATH + "/" +createUser.getId());

		//가져오는 오브젝트가 없으면 user객체에 null만 들어감
		User user = restTemplate.getForObject(PATH+ "/" +createUser.getId(), User.class);

		assertThat(user.getId(), is(nullValue()));
		assertThat(user.getName(), is(nullValue()));
		assertThat(user.getPassword(), is(nullValue()));

	}

	private void validate(String name, String password, User createUser) {
		User resultUser = restTemplate.getForObject(PATH + "/" +createUser.getId(), User.class);
		assertThat(resultUser.getName(), is(name));
		assertThat(resultUser.getPassword(), is(password));
	}

	private User createUser(String name, String password) {
		User user = new User();
		user.setName(name);
		user.setPassword(password);
		return user;
	}

}
