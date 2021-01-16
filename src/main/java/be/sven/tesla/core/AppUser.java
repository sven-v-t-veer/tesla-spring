package be.sven.tesla.core;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class AppUser {

    @Id
    @GeneratedValue
    private Long userId;
    @Column(unique = true)
    private String email;
    private int password;
    private String teslaEmail;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="user_id")
    private List<Vehicle> vehicles;

}
