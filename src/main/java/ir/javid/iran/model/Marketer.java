package ir.javid.iran.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

/**
 * author: Mr.javidmolaei
 */

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "marketers")
public class Marketer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String education;

    private String activityCities;

    @Transient
    @JsonIgnore
    private MultipartFile marketerPicture;

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] marketerPictureFile;

    private String marketerPictureText;

    @Transient
    @JsonIgnore
    private MultipartFile birthCertificatePicture;

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] birthCertificatePictureFile;

    private String birthCertificatePictureText;

    @Transient
    @JsonIgnore
    private MultipartFile affidavitPicture;

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] affidavitPictureFile;

    private String affidavitPictureText;

    @Transient
    @JsonIgnore
    private MultipartFile nationalCardPicture;

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] nationalCardPictureFile;

    private String nationalCardPictureText;

    @Enumerated(EnumType.STRING)
    private MarketerActivityLines activityLine;

    private Boolean adminConfirmation = false;
    //    @OneToMany(mappedBy = "marketer", cascade = CascadeType.ALL)
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "marketer_state",
            joinColumns = @JoinColumn(name = "marketer_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "state_id", referencedColumnName = "id")
    )
    private Set<State> state;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}

