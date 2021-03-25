package vsb.phone_book.service;

import vsb.phone_book.model.pbEntry;

import java.util.List;

public interface pbEntryService {

    void create(pbEntry entry);

    List<pbEntry> readAll();

    pbEntry read(int id);

    boolean update(pbEntry entry, int id);

    boolean delete(int id);
}
