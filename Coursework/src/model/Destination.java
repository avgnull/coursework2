package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Destination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String startCity;
    private long startLn;
    private long startLat;
    private String endCity;
    private long endLn;
    private long endLat;
    private LocalDate dateCreated;
    private LocalDate dateUpdated;
    @OneToMany(mappedBy = "destination", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Checkpoint> checkpointList;
    @OneToOne(mappedBy = "currentDestination",cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Truck truck;
    @ManyToMany
    private List<Manager> responsibleManagers;
    @OneToOne(mappedBy = "destination")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Cargo cargo;

    public Destination(String startCity, long startLn, long startLat, String endCity, long endLn, long endLat, List<Checkpoint> checkpointList, List<Manager> responsibleManagers) {
        this.startCity = startCity;
        this.startLn = startLn;
        this.startLat = startLat;
        this.endCity = endCity;
        this.endLn = endLn;
        this.endLat = endLat;
        this.checkpointList = checkpointList;
        this.responsibleManagers = responsibleManagers;
    }

    public Destination(int id, String startCity, long startLn, long startLat, String endCity, long endLn, long endLat, List<Checkpoint> checkpointList, Truck truck, List<Manager> responsibleManagers, Cargo cargo) {
        this.id = id;
        this.startCity = startCity;
        this.startLn = startLn;
        this.startLat = startLat;
        this.endCity = endCity;
        this.endLn = endLn;
        this.endLat = endLat;
        this.checkpointList = checkpointList;
        this.truck = truck;
        this.responsibleManagers = responsibleManagers;
        this.cargo = cargo;
    }

    public Destination(String startCity, long startLn, long startLat, String endCity, long endLn, long endLat, Truck truck, Cargo cargo) {
        this.startCity = startCity;
        this.startLn = startLn;
        this.startLat = startLat;
        this.endCity = endCity;
        this.endLn = endLn;
        this.endLat = endLat;
        this.truck = truck;
        this.cargo = cargo;
        this.dateCreated = LocalDate.now();
    }
}
