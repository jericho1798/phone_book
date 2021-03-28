package vsb.phone_book.service;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import vsb.phone_book.model.User;
import vsb.phone_book.model.pbEntry;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class pbEntryServiceImplTest {
    private pbEntryServiceImpl pbEntryService;
    private UserServiceImpl userService;
    private User user;
    private pbEntry entry;

    @BeforeEach
    void setUp() {
        pbEntryService = new pbEntryServiceImpl();
        userService = new UserServiceImpl();
        entry = new pbEntry();
        user = new User();
        user.setName("User1");
        entry.setName("entry1");
        entry.setNumber("88127407761");
        user.setPHONE_BOOK(entry);
        userService.create(user);

    }

    @AfterEach
    void tearDown() {
        int i = 0;
        while(!userService.readAll().isEmpty()) {
            userService.delete(i++);
        }
    }

    @Test
    void createShouldBeCorrect() {
        pbEntry entry2 = new pbEntry();
        entry2.setName("Entry2");
        entry2.setNumber("8546258");
        pbEntryService.create(user, entry2);
        Assert.assertEquals(user.getPHONE_BOOK().size(), 2);
    }

    @Test
    void createBadEntry() {
        pbEntry entry2 = new pbEntry();
        pbEntry entry3 = new pbEntry();
        pbEntry entry4 = new pbEntry();
        entry3.setName("");
        entry3.setNumber("8546258");
        entry4.setName("Entry 1");
        entry4.setNumber("8854a52");
        pbEntryService.create(user, entry2);
        Assert.assertEquals(user.getPHONE_BOOK().size(), 1);
    }

    @Test
    void readAllEntry() {
        List<pbEntry> actual = new ArrayList<>();
        actual.add(entry);
        Assert.assertEquals(pbEntryService.readAllEntry(user), actual);
    }

    @Test
    void readEntryShouldBeCorrect() {
        Assert.assertEquals(user.getPHONE_BOOK().get(1), entry);
    }

    @Test
    void readEntryShouldBeNull() {
        Assert.assertEquals(user.getPHONE_BOOK().get(-1), null);
    }

    @Test
    void updateEntryShouldBeTrue() {
        pbEntry eNew = new pbEntry();
        eNew.setName("New");
        eNew.setNumber("88005553535");
        Assert.assertEquals(pbEntryService.updateEntry(user,eNew, 1), true);
    }

    @Test
    void updateEntryBadEntry() {
        pbEntry eNew = new pbEntry();
        eNew.setName("New");
        eNew.setNumber("88005a553535");
        Assert.assertEquals(pbEntryService.updateEntry(user,eNew, 1), false);
    }

    @Test
    void deleteEntryBadID() {
        Assert.assertEquals(pbEntryService.deleteEntry(user, -1), false);
    }

    @Test
    void deleteEntryCorrect() {
        Assert.assertEquals(pbEntryService.deleteEntry(user, 1), true);
    }

    @Test
    void findByNumber() {
        List<pbEntry> actual = new ArrayList<>();
        actual.add(entry);
        Assert.assertEquals(pbEntryService.findByNumber(user, "88127407761"), actual);
    }

}