package vsb.phone_book;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import vsb.phone_book.model.User;
import vsb.phone_book.model.pbEntry;
import vsb.phone_book.service.UserService;
import vsb.phone_book.service.UserServiceImpl;
import vsb.phone_book.service.pbEntryService;
import vsb.phone_book.service.pbEntryServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class UserControllerTest {

    private UserController userController;

    private User user;
    private pbEntry entry;
    private List<String> actual;
    private List<pbEntry> actualE;

    @Autowired
    private  UserService userService;
    @Autowired
    private  pbEntryService pbEntryService;


    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl();
        actual = new ArrayList<>();
        actualE = new ArrayList<>();
        pbEntryService = new pbEntryServiceImpl();
        userController = new UserController(userService, pbEntryService);
        user = new User();
        entry = new pbEntry();
        user.setName("User1");
        entry.setName("entry1");
        entry.setNumber("88127407761");
        user.setPHONE_BOOK(entry);
        actual.add(user.getName());
        actualE.add(entry);
        userController.create(user);
    }

    @AfterEach
    void tearDown() {
        userController.delete(user.getId());
        actual.clear();
        actualE.clear();
    }

    @Test
    void create() {
        User userNew = new User();
        userNew.setName("");
        Assert.assertEquals(userController.create(userNew).getStatusCode(), HttpStatus.NO_CONTENT);
        userNew.setName("Name");
        Assert.assertEquals(userController.create(userNew).getStatusCode(), HttpStatus.CREATED);
        userController.delete(userNew.getId());
    }

    @Test
    void readAll() {
        Assert.assertEquals(userController.readAll().getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(userController.readAll().getBody(), actual);
    }

    @Test
    void read() {
        Assert.assertEquals(userController.read(user.getId()).getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(userController.read(user.getId()).getBody(), user);
        Assert.assertEquals(userController.read(-1).getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void update() {
        User userNew = new User();
        userNew.setName("");
        Assert.assertEquals(userController.update(user.getId(), userNew).getStatusCode(), HttpStatus.NOT_MODIFIED);
        userNew.setName("Name");
        Assert.assertEquals(userController.update(user.getId(), userNew).getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(userController.update(-1, userNew).getStatusCode(), HttpStatus.NOT_MODIFIED);
    }

    @Test
    void delete() {
        Assert.assertEquals(userController.delete(-1).getStatusCode(), HttpStatus.NOT_MODIFIED);
        Assert.assertEquals(userController.delete(user.getId()).getStatusCode(), HttpStatus.OK);
    }

    @Test
    void findByName() {
        Assert.assertEquals(userController.findByName("user1").getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(userController.findByName("user1").getBody(), new ArrayList<User>(Arrays.asList(user)));
        Assert.assertEquals(userController.findByName("qwe").getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void createEntry() {
        pbEntry et = new pbEntry();
        Assert.assertEquals(userController.createEntry(user.getId(), entry).getStatusCode(), HttpStatus.CREATED);
        Assert.assertEquals(userController.createEntry(user.getId(), et).getStatusCode(), HttpStatus.NO_CONTENT);
        Assert.assertEquals(userController.createEntry(-1, entry).getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void readAllEntry() {
        Assert.assertEquals(userController.readAllEntry(user.getId()).getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(userController.readAllEntry(user.getId()).getBody(), actualE);
        Assert.assertEquals(userController.readAllEntry(-1).getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void readEntry() {
        Assert.assertEquals(userController.readEntry(user.getId(), entry.getId()).getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(userController.readEntry(user.getId(), entry.getId()).getBody(), entry);
        Assert.assertEquals(userController.readEntry(user.getId(), -1).getStatusCode(), HttpStatus.NOT_FOUND);
        Assert.assertEquals(userController.readEntry(-1, entry.getId()).getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void updateEntry() {
        pbEntry et = new pbEntry();
        Assert.assertEquals(userController.updateEntry(user.getId(), entry.getId(), entry).getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(userController.updateEntry(user.getId(), entry.getId(), et).getStatusCode(), HttpStatus.NOT_MODIFIED);
    }

    @Test
    void deleteEntry() {
        Assert.assertEquals(userController.deleteEntry(user.getId(), entry.getId()).getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(userController.deleteEntry(user.getId(), -1).getStatusCode(), HttpStatus.NOT_MODIFIED);
        Assert.assertEquals(userController.deleteEntry(-1, entry.getId()).getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void findByNumber() {
        Assert.assertEquals(userController.findByNumber(user.getId(), "12345").getStatusCode(), HttpStatus.NOT_FOUND);
        Assert.assertEquals(userController.findByNumber(user.getId(), "88127407761").getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(userController.findByNumber(user.getId(), "88127407761").getBody(), actualE);
        Assert.assertEquals(userController.findByNumber(-1, "").getStatusCode(), HttpStatus.NOT_FOUND);
    }

}