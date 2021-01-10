package be.sven.tesla.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
public class AppUser {

    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private int password;
    private String teslaEmail;
    @OneToMany(mappedBy = "id")
    private List<Vehicle> vehicles;

}
