package peaksoft.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    @Id
    @GeneratedValue(generator = "category_gen",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "category_gen",sequenceName = "category_seq", allocationSize = 1)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "category",cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE})
    private List<SubCategory> subCategories;
}
