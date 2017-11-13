package travelcompare.restapi.model;

import lombok.*;

import javax.persistence.*;

@Entity
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private long id;

    @Column
    @Getter
    @Setter
    private long googleId;

    @Column(nullable = false)
    @Getter
    @Setter
    @NonNull
    private String firstName;

    @Column(nullable = false)
    @Getter
    @Setter
    @NonNull
    private String lastName;

    @Column(nullable = false)
    @Getter
    @Setter
    @NonNull
    private String email;

    @Column
    @Getter
    @Setter
    private String originCountry;

}
