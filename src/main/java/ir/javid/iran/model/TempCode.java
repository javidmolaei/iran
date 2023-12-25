package ir.javid.iran.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author : javid molaei 7/2/2022
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TEMP_CODE")
public class TempCode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String verifyPhoneCode;
    private String verifyEmailCode;
    private Date verifyDate;

    @OneToOne
    @JoinColumn(name = "USER_ID")
    private User user;


    public TempCode(String verifyPhoneCode, Date verifyDate, User user) {
        this.verifyPhoneCode = verifyPhoneCode;
        this.verifyDate = verifyDate;
        this.user = user;
    }

    public TempCode(Date verifyDate, User user) {
        this.verifyDate = verifyDate;
        this.user = user;
    }
}
