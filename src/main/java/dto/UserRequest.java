package dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRequest {

    @NotBlank(message = "Nome obrigatorio")
    private String name;

    @NotNull(message = "Idade obrigatorio")
    private Integer age;

    public UserRequest() {
    }

}
