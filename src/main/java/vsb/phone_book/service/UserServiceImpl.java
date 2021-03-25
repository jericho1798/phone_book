package vsb.phone_book.service;

import org.springframework.stereotype.Service;
import vsb.phone_book.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class UserServiceImpl implements UserService {

    //User Repo
    private static final Map<Integer, User> USER_REPO = new HashMap<>();

    //ID gen
    private static  final AtomicInteger USER_ID_HOLDER = new AtomicInteger();

    @Override
    public void create(User user) {
        final  int userId = USER_ID_HOLDER.incrementAndGet();
        user.setId(userId);
        USER_REPO.put(userId, user);
    }

    @Override
    public List<User> readAll() {
        return new ArrayList<User>(USER_REPO.values());
    }

    @Override
    public User read(int id) {
        return USER_REPO.get(id);
    }

    @Override
    public boolean update(User user, int id) {
        if(USER_REPO.containsKey(id)) {
            user.setId(id);
            USER_REPO.put(id, user);
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        return USER_REPO.remove(id) != null;
    }
}
