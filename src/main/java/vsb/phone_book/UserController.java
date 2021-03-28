package vsb.phone_book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vsb.phone_book.model.User;
import vsb.phone_book.model.pbEntry;
import vsb.phone_book.service.UserService;
import vsb.phone_book.service.pbEntryService;

import java.util.List;



@RestController
public class UserController {

    private final UserService userService;
    private final pbEntryService pbEntryService;

    @Autowired
    public UserController(UserService userService, pbEntryService pbEntryService) {
        this.userService = userService;
        this.pbEntryService = pbEntryService;
    }



    //User create
    @PostMapping("/users")
    public ResponseEntity<?> create(@RequestBody User user) {
        return userService.create(user)
                ? new ResponseEntity<>(HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //User readAll
    @GetMapping("/users")
    public ResponseEntity<List<String>> readAll() {

        final List<String> users = userService.readAll();

        return users != null && !users.isEmpty()
                ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //User read
    @GetMapping("/users/{id}")
    public ResponseEntity<User> read(@PathVariable(name = "id") int id) {
        final  User user = userService.read(id);

        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //User update
    @PutMapping("/users/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") int id,
                                    @RequestBody User user) {

        final boolean updated = userService.update(user, id);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    //User delete
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {

        final boolean deleted = userService.delete(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    //User search by name or part of it
    @GetMapping("/users/findByName/{name}")
    public ResponseEntity<List<User>> findByName(@PathVariable(name = "name") String name) {

        final List<User> matchList = userService.findByName(name);
        return !matchList.isEmpty()
                ? new ResponseEntity<>(matchList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //Entry create
    @PostMapping("/users/{id}/add")
    public ResponseEntity<?> createEntry(@PathVariable(name = "id") int id,
                                         @RequestBody pbEntry entry) {
        if(userService.read(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return pbEntryService.create(userService.read(id), entry)
                    ? new ResponseEntity<>(HttpStatus.CREATED)
                    : new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    //Entry readAll
    @GetMapping("/users/{id}/all")
    public ResponseEntity<List<pbEntry>> readAllEntry(@PathVariable(name = "id") int id) {

        if(userService.read(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            final List<pbEntry> entries = pbEntryService.readAllEntry(userService.read(id));

            return entries != null && !entries.isEmpty()
                    ? new ResponseEntity<>(entries, HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Entry read
    @GetMapping("/users/{id}/{eId}")
    public ResponseEntity<pbEntry> readEntry(@PathVariable(name = "id") int id,
                                             @PathVariable(name = "eId") int eId) {

        if(userService.read(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            final User user = userService.read(id);
            final pbEntry entry = user.getPHONE_BOOK().get(eId);
            return entry != null
                    ? new ResponseEntity<>(entry, HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Entry update
    @PutMapping("/users/{id}/{eId}")
    public ResponseEntity<?> updateEntry(@PathVariable(name = "id") int id,
                                         @PathVariable(name = "eId") int eId,
                                         @RequestBody pbEntry entry) {

        if(userService.read(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            final User user = userService.read(id);
            final boolean updated = pbEntryService.updateEntry(user, entry, eId);
            return updated
                    ? new ResponseEntity<>(HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    //Entry delete
    @DeleteMapping("/users/{id}/{eId}")
    public ResponseEntity<?> deleteEntry(@PathVariable(name = "id") int id,
                                         @PathVariable(name = "eId") int eId) {

        if(userService.read(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            final User user = userService.read(id);
            final boolean deleted = pbEntryService.deleteEntry(user, eId);
            return deleted
                    ? new ResponseEntity<>(HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    //Entry search by phone number
    @GetMapping("/users/{id}/findByNum/{num}")
    public ResponseEntity<List<pbEntry>> findByNumber(@PathVariable(name = "id") int id,
                                                      @PathVariable(name = "num") String num) {

        if(userService.read(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            final User user = userService.read(id);
            final List<pbEntry> entries = pbEntryService.findByNumber(user, num);
            return !entries.isEmpty()
                    ? new ResponseEntity<>(entries, HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
