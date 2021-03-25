package com.kozyrevych.app.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "usr")
public class User {
    @Column(name = "usr_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "phone_number")
    @Size(min = 2, max = 15, message = "Incorrect number")
    private String phoneNumber = "";

    @Email(message = "Incorrect email")
    private String email = "";

    @Size(min = 2, max = 30, message = "Incorrect size")
    private String name = "";

    @Size(max = 50, message = "Incorrect size")
    private String password = "";

    @Temporal(TemporalType.DATE)
    private Date birthday;

    @Column(name = "registration_date")
    @Temporal(TemporalType.DATE)
    @NotNull(message = "Empty date")
    private Date registrationDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FreePlace> freePlaces;

    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
    private Set<CurrentFilmData> currentFilmsData;
}
