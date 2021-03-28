package vsb.phone_book.service;

import org.springframework.stereotype.Service;
import vsb.phone_book.model.User;
import vsb.phone_book.model.pbEntry;

import java.util.ArrayList;
import java.util.List;

@Service
public class pbEntryServiceImpl implements pbEntryService {

    @Override
    public boolean create(User user, pbEntry entry) {
        if(entry.getNumber()!=null && !entry.getNumber().isEmpty()
                && entry.getName()!=null && !entry.getName().isEmpty()
                && entry.getNumber().matches("\\d+")) {
            user.setPHONE_BOOK(entry);
            return true;
        }
        return false;
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
            if(entry.getNumber() != null && !entry.getNumber().isEmpty()
                    && entry.getName() != null && !entry.getName().isEmpty()
                    && entry.getNumber().matches("\\d+")) {
                entry.setId(eId);
                user.getPHONE_BOOK().put(eId, entry);
            } else {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteEntry(User user, int eId) {
        return user.getPHONE_BOOK().remove(eId) != null;
    }

    @Override
    public List<pbEntry> findByNumber(User user, String number) {
        ArrayList<pbEntry> entries = new ArrayList<>();
        for(pbEntry entry : user.getPHONE_BOOK().values()) {
            if(entry.getNumber().equals(number)) {
                entries.add(entry);
            }
        }
        return entries;
    }
}
