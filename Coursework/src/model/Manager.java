package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Manager extends User implements Serializable {
    private String email;
    private LocalDate employmentDate;
    private boolean isAdmin;
    @ManyToMany(mappedBy = "responsibleManagers", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Destination> myManagedDestinations;

    public Manager(String login, String password, String name, String surname, LocalDate birthDate, String phoneNum, String email) {
        super(login, password, name, surname, birthDate, phoneNum);
        this.employmentDate = LocalDate.now();
        this.email = email;
        this.isAdmin=false;
    }

    public Manager(String login, String password, String name, String surname, LocalDate birthDate, String phoneNum, String email, boolean isAdmin) {
        super(login, password, name, surname, birthDate, phoneNum);
        this.email = email;
        this.employmentDate = LocalDate.now();
        this.isAdmin = isAdmin;
    }


}
