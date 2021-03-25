package vsb.phone_book.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class pbEntry {

    @NotNull
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String number;
}
