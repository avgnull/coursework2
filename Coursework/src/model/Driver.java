package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Driver extends User implements Serializable {
    private LocalDate medCertificateDate;
    private String medCertificateNumber;
    private String driverLicense;

    public Driver(String login, String password, String name, String surname, LocalDate birthDate, String phoneNum, LocalDate medCertificateDate, String medCertificateNumber, String driverLicense) {
        super(login, password, name, surname, birthDate, phoneNum);
        this.medCertificateDate = medCertificateDate;
        this.medCertificateNumber = medCertificateNumber;
        this.driverLicense = driverLicense;
    }
}
