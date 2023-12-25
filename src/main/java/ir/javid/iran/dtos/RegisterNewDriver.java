package ir.javid.iran.dtos;

import ir.javid.iran.model.Role;
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
public class RegisterNewDriver {
    private String name;
    private String family;
    private String username;
    private String email;
    private String password;
    private String address;
    private String plateNumber;
    private Long plate;
    private String carColor;
    private String carModel;
    private String carBrand;
    private Long capacity;
}
