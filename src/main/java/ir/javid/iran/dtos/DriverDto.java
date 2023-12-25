package ir.javid.iran.dtos;

import jakarta.persistence.*;
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
public class DriverDto {

    private String plateNumber;
    private Long plate;
    private String carColor;
    private String carModel;
    private String carBrand;
    private Long capacity;
}
