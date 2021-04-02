package com.kozyrevych.app.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;
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

    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender = Gender.MALE;

    @Column(name = "phone_number")
    @Size(min = 2, max = 15, message = "Incorrect number")
    private String phoneNumber = "";

    @Email(message = "Incorrect email")
    private String email = "";

    @Size(min = 2, max = 30, message = "Incorrect size")
    private String name = "";

    @Size(max = 50, message = "Incorrect size")
    @NotNull
    private String password = "";

    private LocalDate birthday;

    @Column(name = "registration_date")
    @NotNull(message = "Empty date")
    private LocalDate registrationDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FreePlace> freePlaces;

    @ManyToMany(fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinTable(name = "CurrentFilmAndUser",
            joinColumns = @JoinColumn(name = "usr_id"),
            inverseJoinColumns = @JoinColumn(name = "current_film_data_id")
    )
    private Set<CurrentFilmData> currentFilmsData;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return gender == user.gender && phoneNumber.equals(user.phoneNumber) && email.equals(user.email) && name.equals(user.name) && password.equals(user.password) && birthday.equals(user.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gender, phoneNumber, email, name, password, birthday);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", gender=" + gender.name() +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", birthday=" + birthday +
                '}';
    }

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "usr_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public void removeCurrentFilmData(CurrentFilmData currentFilmData) {
        currentFilmsData.removeIf(o -> o.getId() == currentFilmData.getId());
    }
}
