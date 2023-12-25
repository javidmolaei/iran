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
@Table(name = "product_owner")
public class ProductOwner {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String economicCode;

    //    @ManyToOne
//    @JoinColumn(name = "state_id")
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "owner_state",
            joinColumns = @JoinColumn(name = "owner_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "state_id", referencedColumnName = "id")
    )
    private Set<State> state;

    private String ceoName;

    @Transient
    @JsonIgnore
    private MultipartFile activityLicensePicture;

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] activityLicensePictureFile;

    private String activityLicensePictureText;

    private String signatureOwners;

    @Transient
    @JsonIgnore
    private MultipartFile companyLogo;

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] companyLogoFile;

    private String companyLogoText;

    private Boolean adminConfirmation = false;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}
