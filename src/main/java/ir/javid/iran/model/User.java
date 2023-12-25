package ir.javid.iran.model;

import jakarta.persistence.*;

import java.util.Set;

/**
 * author: Mr.javidmolaei
 */

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
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

    public User(String name, String username, String email, String password, String address) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.address = address;
    }

    public User(String name, String family, String username, String email, String password, String address) {
        this.name = name;
        this.family = family;
        this.username = username;
        this.email = email;
        this.password = password;
        this.address = address;
    }
}
