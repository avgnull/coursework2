package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private LocalDate dateCreated;
    private LocalDate dateUpdated;
    private double weight;
    private CargoType cargoType;
    private String description;
    private String customer;
    @OneToOne
    private Destination destination;

    public Cargo(String title, double weight, CargoType cargoType, String description, String customer) {
        this.title = title;
        this.weight = weight;
        this.cargoType = cargoType;
        this.description = description;
        this.customer = customer;
        this.dateCreated = LocalDate.now();
        this.dateUpdated = null;
    }

    @Override
    public String toString() {
        return title + " " + id;
    }
}
