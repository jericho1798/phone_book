package vsb.phone_book.service;

import org.springframework.stereotype.Service;
import vsb.phone_book.model.User;
import vsb.phone_book.model.pbEntry;

import java.util.ArrayList;
import java.util.List;

@Service
public class pbEntryServiceImpl implements pbEntryService {
    @Override
    public void create(User user, pbEntry entry) {
        user.setPHONE_BOOK(entry);
    }

    @Override
    public List<pbEntry> readAllEntry(User user) {

        return new ArrayList<pbEntry>(user.getPHONE_BOOK().values());
    }

    @Override
    public pbEntry readEntry(User user, int eId) {
        return user.getPHONE_BOOK().get(eId);
    }

    @Override
    public boolean updateEntry(User user, pbEntry entry, int eId) {
        if(user.getPHONE_BOOK().containsKey(eId)) {
            entry.setId(eId);
            user.getPHONE_BOOK().put(eId, entry);
        }
        return false;
    }

    @Override
    public boolean deleteEntry(User user, int eId) {
        return user.getPHONE_BOOK().remove(eId) != null;
    }
}
