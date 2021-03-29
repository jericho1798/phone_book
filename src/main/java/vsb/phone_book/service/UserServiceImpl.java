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
    public boolean create(User user) {
        if(user.getName() != null && !user.getName().isEmpty()) {
            final  int userId = USER_ID_HOLDER.incrementAndGet();
            user.setId(userId);
            USER_REPO.put(userId, user);
            return true;
        }
        return false;
    }

    @Override
    public List<String> readAll() {
        ArrayList<String> users = new ArrayList<>();
        for(User a: USER_REPO.values()) {
            users.add(a.getName());
        }
        return  users;
    }

    @Override
    public User read(int id) {
        return USER_REPO.get(id);
    }

    @Override
    public boolean update(User user, int id) {
        if(USER_REPO.containsKey(id)) {
            if(user.getName() != null && !user.getName().isEmpty()) {
                user.setId(id);
                if(user.getPHONE_BOOK() != null && !user.getPHONE_BOOK().isEmpty()) {
                    USER_REPO.put(id, user);
                } else {
                    user.setPB(USER_REPO.get(id).getPHONE_BOOK());
                    USER_REPO.put(id, user);
                }
            } else {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        return USER_REPO.remove(id) != null;
    }

    @Override
    public List <User> findByName(String name) {
        ArrayList<User> matchList = new ArrayList<>();
        for(User user : USER_REPO.values()) {
            if(user.getName().toLowerCase().contains(name.toLowerCase())) {
                matchList.add(user);
            }
        }
        return matchList;
    }
}

