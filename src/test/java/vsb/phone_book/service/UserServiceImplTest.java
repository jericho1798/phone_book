package vsb.phone_book.service;


import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import vsb.phone_book.model.User;
import vsb.phone_book.model.pbEntry;

import java.util.ArrayList;
import java.util.List;


class UserServiceImplTest {

    private List<String> actual;
    private UserServiceImpl userService;
    private User user1, user2;

    @BeforeEach
    public void setUp() {
        userService = new UserServiceImpl();
        actual = new ArrayList<>();
        user1 = new User();
        user1.setName("User1");
        userService.create(user1);
        user2 = new User();
        user2.setName("User2");
        userService.create(user2);
    }

    @AfterEach
    public void teardown() {
        actual.clear();
        int i = 0;
        while(!userService.readAll().isEmpty()) {
            userService.delete(i++);
        }
    }

    @Test
    void createUserWithNullOrEmptyName() {
        List<String> before = userService.readAll();
        User userNull = new User();
        User userEmpty = new User();
        userEmpty.setName("");
        userService.create(userNull);
        userService.create(userEmpty);
        List<String> after = userService.readAll();
        Assert.assertEquals(before, after);

    }

    @Test
    void readAllUsersTest() {
        List<String> expected = userService.readAll();
        actual.add(user1.getName());
        actual.add(user2.getName());
        Assert.assertEquals(expected, actual);
    }

    @Test
    void readByIdTestShouldBeCorrect() {
        Assert.assertEquals(userService.read(user1.getId()), user1);
    }

    @Test
    void readByIdTestShouldBeNull() {
        Assert.assertEquals(userService.read(-1), null);
    }

    @Test
    void updateUserByIdShouldBeTrue() {
        User userNew = new User();
        userNew.setName("UserNew");
        Assert.assertEquals(userService.update(userNew, user1.getId()), true);
    }

    @Test
    void updateUserByIdShouldBeFalse() {
        User userNew = new User();
        userNew.setName("");
        Assert.assertEquals(userService.update(userNew, user1.getId()), false);
    }

    @Test
    void updateUserByIdShouldBeFalseNullName() {
        User userNew = new User();
        Assert.assertEquals(userService.update(userNew, user1.getId()), false);
    }

    @Test
    void updateUserByIdNoSuchId() {
        User userNew = new User();
        userNew.setName("asd");
        Assert.assertEquals(userService.update(userNew, -1), false);
    }

    @Test
    void deleteUserBadId() {
        Assert.assertEquals(userService.delete(-1), false);
    }

    @Test
    void deleteUserCorrect() {
        Assert.assertEquals(userService.delete(user1.getId()), true);
    }

    @Test
    void findByNameUser1() {
        List<User> actual = new ArrayList<>();
        actual.add(user1);
        Assert.assertEquals(userService.findByName("User1"), actual);
    }

    @Test
    void findByNameUser() {
        List<User> actual = new ArrayList<>();
        actual.add(user1);
        actual.add(user2);
        Assert.assertEquals(userService.findByName("User"), actual);
    }

    @Test
    void findByNameLC() {
        List<User> act = new ArrayList<>();
        act.add(user1);
        Assert.assertEquals(userService.findByName("user1"), act);
    }


}