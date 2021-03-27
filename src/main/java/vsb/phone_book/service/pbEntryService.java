package vsb.phone_book.service;

import vsb.phone_book.model.User;
import vsb.phone_book.model.pbEntry;

import java.util.List;

public interface pbEntryService {

    void create(User user, pbEntry entry);

    List<pbEntry> readAllEntry(User user);

    pbEntry readEntry(User user, int eId);

    boolean updateEntry(User user, pbEntry entry, int eId);

    boolean deleteEntry(User user, int eId);

    List <pbEntry> findByNumber(User user, String number);
}
