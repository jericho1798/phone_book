package vsb.phone_book.model;

import lombok.Data;


import java.util.HashMap;

@Data
public class User {

    private Integer id;

    private String name;

    private HashMap<Integer, pbEntry> PHONE_BOOK = new HashMap<>();

    public void setPHONE_BOOK(pbEntry entry) {
        int i = PHONE_BOOK.size() + 1;
        entry.setId(i);
        PHONE_BOOK.put(i, entry);
    }

}
