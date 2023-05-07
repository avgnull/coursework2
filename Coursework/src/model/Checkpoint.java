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
public class Checkpoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private boolean longStop;
    private LocalDate dateArrived;
    @ManyToOne
    private Destination destination;

    public Checkpoint(String title, boolean longStop, LocalDate dateArrived) {
        this.title = title;
        this.longStop = longStop;
        this.dateArrived = dateArrived;
    }

    public Checkpoint(String title, boolean longStop) {
        this.title = title;
        this.longStop = longStop;
    }

    @Override
    public String toString() {
        return title + " " + id;
    }
}
