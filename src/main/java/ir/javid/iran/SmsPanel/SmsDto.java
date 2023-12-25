package ir.javid.iran.SmsPanel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * author: Mr.javidmolaei
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsDto {
    private List<String> recipient;
    private String sender = "+983000505";
    private String message;

    public SmsDto(List<String> recipient, String message) {
        this.recipient = recipient;
        this.message = message;
    }
}
