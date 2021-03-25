package vsb.phone_book.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
public class User {

    @NotNull(message = "Set ID")
    private Integer id;

    @NotNull(message = "Enter a name!")
    private String name;

    private Map<Integer, pbEntry> PHONE_BOOK;
}
