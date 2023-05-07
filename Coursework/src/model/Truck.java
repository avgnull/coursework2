package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Truck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String make;
    private String model;
    private int year;
    private double odometer;
    private double fuelTankCapacity;
    private TyreType tyreType;
    @OneToOne
    private Destination currentDestination;

    public Truck(String make, String model, int year, double odometer, double fuelTankCapacity, TyreType tyreType) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.odometer = odometer;
        this.fuelTankCapacity = fuelTankCapacity;
        this.tyreType = tyreType;
    }

    @Override
    public String toString() {
        return make + " " + model + " " + year;
    }
}
