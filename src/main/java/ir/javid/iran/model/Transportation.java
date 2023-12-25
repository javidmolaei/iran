package ir.javid.iran.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * author: Mr.javidmolaei
 */

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transportation")
public class Transportation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String economicCode;
    //    @OneToMany(mappedBy = "transportation", cascade = CascadeType.ALL)
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "transportation_state",
            joinColumns = @JoinColumn(name = "transportation_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "state_id", referencedColumnName = "id")
    )
    private Set<State> state;
    //    @OneToMany(mappedBy = "transportation", cascade = CascadeType.ALL)
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "transportation_country",
            joinColumns = @JoinColumn(name = "transportation_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "country_id", referencedColumnName = "id")
    )
    private Set<Country> country;
    private String ceoName;
    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] activityLicensePictureFile;
    private String activityLicensePicture;
    private String signatureOwners;
    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] companyLogoFile;
    private String companyLogo;
    private Boolean adminConfirmation = false;
    @Enumerated(EnumType.STRING)
    private ActivityStatus placesActivity;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
