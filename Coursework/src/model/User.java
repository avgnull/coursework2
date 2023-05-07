package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public abstract class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;
    private String login;
    private String password;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private String phoneNum;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> myComments;

    public User(String login, String password, String name, String surname, LocalDate birthDate, String phoneNum) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.phoneNum = phoneNum;
    }

    @Override
    public String toString() {
        return "User{" + id + " " + login + " " + password + " " + name + " " + surname + " " + birthDate + " " + phoneNum + "}";
    }
}
