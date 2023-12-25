package ir.javid.iran.model;

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
@Entity
@Table(name = "countries")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

//    @ManyToOne
//    @JoinColumn(name = "transportation_id")
//    private Transportation transportation;

    public Country(String name) {
        this.name = name;
    }
}
