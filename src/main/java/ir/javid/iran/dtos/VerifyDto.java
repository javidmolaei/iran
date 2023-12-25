package ir.javid.iran.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * author: Dr.mahyartolooie
 */

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VerifyDto {
    private String value1;
    private String value2;
    private String value3;
    private String value4;

    @Override
    public String toString() {
        return "VerifyDto{" +
                "value1='" + value1 + '\'' +
                ", value2='" + value2 + '\'' +
                ", value3='" + value3 + '\'' +
                ", value4='" + value4 + '\'' +
                '}';
    }
}
