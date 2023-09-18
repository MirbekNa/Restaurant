package peaksoft.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "stopLists")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StopList {
    @Id
    @GeneratedValue(generator = "stopList_gen",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "stopList_gen",sequenceName = "stopList_seq", allocationSize = 1)
    private Long id;
    private String reason;
    private LocalDate date;
    @OneToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    private MenuItem menuItem;
}
