package ir.javid.iran.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ir.javid.iran.model.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MarketerProductOwnerDto {

    private String name;
    private String family;
    private String fatherName;
    private String phoneNumber;
    private Boolean verifyPhone = false;
    private String postalCode;
    private String nationalCode;
    private String address;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    private Boolean verifyEmail = false;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToOne(mappedBy = "user")
    private TempCode tempCode;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles;

    private String economicCode;

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
