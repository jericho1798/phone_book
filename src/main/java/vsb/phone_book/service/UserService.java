package vsb.phone_book.service;

import vsb.phone_book.model.User;

import java.util.List;

public interface UserService {

    void create(User user);

    List<User> readAll();

    User read(int id);

    boolean update(User user, int id);

    boolean delete(int id);

}
