package tobi;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import springbook.user.dao.UserDao;
import springbook.user.domain.User;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/application-config.xml")
public class DaoTest {
	@Autowired
	private ApplicationContext context;
	private UserDao dao; // @before에서 만든 것을 공유하기 위해 선언한다.
	private User user1;
	private User user2;
	private User user3;

	@Before
	public void setuUp() {
		this.dao = context.getBean("userDao", UserDao.class);
		this.user1 = new User("a", "a", "a");
		this.user2 = new User("b", "b", "b");
		this.user3 = new User("c", "c", "c");
	}

	@Test
	public void daoTest() throws ClassNotFoundException, SQLException {

		System.out.println("시작");

		dao.deleteAll();
		assertThat(dao.getCount(), is(0));

		// 등록
		User user = new User();
		user.setId("mmm");
		user.setName("나dmsyd");
		user.setPassword("P@ss");
		dao.add(user);
		assertThat(dao.getCount(), is(1));

		// 조회
		User user2 = dao.get(user.getId());
		assertThat(user2.getName(), is(user.getName()));
		assertThat(user2.getId(), is(user.getId()));
		assertThat(user2.getPassword(), is(user.getPassword()));

		// 삭제
		dao.delete(user.getId());
		assertThat(dao.getCount(), is(0));
	}

	@Test
	public void count() throws SQLException, ClassNotFoundException {
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));

		dao.add(user1);
		assertThat(dao.getCount(), is(1));

		dao.add(user2);
		assertThat(dao.getCount(), is(2));

		dao.add(user3);
		assertThat(dao.getCount(), is(3));
	}

	@Test(expected = EmptyResultDataAccessException.class)
	public void getUserFailure() throws SQLException, ClassNotFoundException {
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));

		dao.get("unknown_id");
	}
}
