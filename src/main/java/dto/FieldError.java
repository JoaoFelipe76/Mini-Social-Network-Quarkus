package dto;

import lombok.Data;

@Data
public class FieldError {

    String field;
    String message;

    public FieldError(String field, String message) {
        this.field = field;
        this.message = message;
    }

}
