package ir.javid.iran.dtos;

import ir.javid.iran.model.Role;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * author: Mr.javidmolaei
 */

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String name;
    private String username;
    private String email;
    private String password;
    private Role role;
}
