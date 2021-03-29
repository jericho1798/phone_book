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

    /**
     * @Requiremnts:
     * User Name must not be null or Empty
     * Entry Name and Number must not be null or Empty
     */


    /**
     * @param user Must meet the @Requirements
     * @return HttpStatus.Created if User was created otherwise - HttpStatus.NO_CONTENT
     */
    @PostMapping("/users")
    public ResponseEntity<?> create(@RequestBody User user) {
        return userService.create(user)
                ? new ResponseEntity<>(HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    /**
     * @return List of all Users and HttpStatus.OK if it's not null or empty otherwise HttpStatus.NOT_FOUND
     */
    @GetMapping("/users")
    public ResponseEntity<List<String>> readAll() {

        final List<String> users = userService.readAll();

        return users != null && !users.isEmpty()
                ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    /**
     * @param id User id
     * @return User if a User with this Id exists
     */
    @GetMapping("/users/{id}")
    public ResponseEntity<User> read(@PathVariable(name = "id") int id) {
        final  User user = userService.read(id);

        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    /**
     * @param id User Id to update (must exist)
     * @param user User with new parameters (must meet the @Requirements)
     * @return HttpStatusOK if updated otherwise - HttpStatus.NOT_FOUND
     */
    @PutMapping("/users/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") int id,
                                    @RequestBody User user) {

        final boolean updated = userService.update(user, id);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }


    /**
     * @param id User Id to delete
     * @return HttpStatusOK if deleted otherwise - HttpStatus.NOT_FOUND
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {

        final boolean deleted = userService.delete(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }


    /**
     * @param name User name or part of name (in any case)
     * @return List of users whose name contains @param
     */
    @GetMapping("/users/findByName/{name}")
    public ResponseEntity<List<User>> findByName(@PathVariable(name = "name") String name) {

        final List<User> matchList = userService.findByName(name);
        return !matchList.isEmpty()
                ? new ResponseEntity<>(matchList, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    /**
     * @param id Id of the User who owns the phone book
     * @param entry Phone book entry (must meet the @Requirements)
     * @return HttpStatus.NOT_FOUND if User with @id does not exist,
     *         HttpStatus.CREATED if User created otherwise - HttpStatus.NO_CONTENT
     */
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


    /**
     * @param id Id of the User who owns the phone book
     * @return HttpStatus.NOT_FOUND if User with @id does not exist,
     *         List of all entries and HttpStatus.OK if it's not null or empty
     *         otherwise - HttpStatus.NOT_FOUND
     */
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


    /**
     * @param id Id of the User who owns the phone book
     * @param eId Entry id to read
     * @return HttpStatus.NOT_FOUND if User with @id does not exist,
     *         entry and HttpStatus.OK if entry exists
     *         otherwise - HttpStatus.NOT_FOUND
     */
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


    /**
     * @param id Id of the User who owns the phone book
     * @param eId Entry Id to update
     * @param entry Entry with new parameters (must meet the @Requirements)
     * @return HttpStatus.NOT_FOUND if User with @id does not exist,
     *         HttpStatus.OK if updated otherwise - HttpStatus.NOT_MODIFIED
     */
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


    /**
     * @param id Id of the User who owns the phone book
     * @param eId Entry Id to update
     * @return HttpStatus.NOT_FOUND if User with @id does not exist,
     *      *         HttpStatus.OK if deleted otherwise - HttpStatus.NOT_MODIFIED
     */
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


    /**
     * @param id Id of the User who owns the phone book
     * @param num Number to search
     * @return HttpStatus.NOT_FOUND if User with @id does not exist,
     *         List of entries with @num and HttpStatus.OK
     *         otherwise - HttpStatus.NOT_FOUND
     */
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
