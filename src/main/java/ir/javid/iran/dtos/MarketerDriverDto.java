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
public class MarketerDriverDto {

    private Long id;
    private String name;
    private String family;
    private String fatherName;
    private String phoneNumber;
    private Boolean verifyPhone = false;
    private String postalCode;
    private String nationalCode;
    private String address;
    private String email;
    private String username;
    private Boolean verifyEmail = false;
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    @OneToOne(mappedBy = "user")
    private TempCode tempCode;

    private Set<Role> roles;

    private String LicenseNumber;
    private String transitPlate;
    private String activityType;
    private String truckInformation;

    @Transient
    @JsonIgnore
    private MultipartFile driverPicture;
    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] driverPictureFile;
    private String driverPictureString;

    @Transient
    @JsonIgnore
    private MultipartFile nationalCardPicture;
    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] nationalCardPictureFile;
    private String nationalCardPictureString;

    @Transient
    @JsonIgnore
    private MultipartFile licenseCardPicture;
    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] licenseCardPictureFile;
    private String licenseCardPictureString;

    @Transient
    @JsonIgnore
    private MultipartFile carCartPicture;

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] carCartPictureFile;
    private String carCartPictureString;

    private String truckType;
    private String truckUsage;
    private String truckModel;
    private String plateNumber;
    private String smartCardNumber;
    private Boolean commitmentConfirmation;
    @Enumerated(EnumType.STRING)
    private ActivityStatus placesActivity;
    private String favoritePlace;
    private Boolean adminConfirmation = false;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "driver_state",
            joinColumns = @JoinColumn(name = "driver_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "state_id", referencedColumnName = "id")
    )
    private Set<State> state;



    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;


}
