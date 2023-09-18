package peaksoft.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "restaurants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "restaurant_gen")
    @SequenceGenerator(name = "restaurant_gen",sequenceName = "restaurant_seq",allocationSize = 1)
    private Long id;
    private String name;
    private String location;
    private String restType;
    private int numberOfEmployees;
    private int service;
    @OneToMany(mappedBy = "restaurant",cascade = {CascadeType.ALL})
    private List<User> users;
    @OneToMany(mappedBy = "restaurant",cascade = {CascadeType.ALL})
    private List<MenuItem> menuItems;
}
