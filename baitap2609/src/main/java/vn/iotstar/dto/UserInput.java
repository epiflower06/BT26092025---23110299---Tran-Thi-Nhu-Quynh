package vn.iotstar.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInput {
    @NotBlank
    private String fullname;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min=6)
    private String password;

    private String phone;

    private String role; // USER/ADMIN


    
}
