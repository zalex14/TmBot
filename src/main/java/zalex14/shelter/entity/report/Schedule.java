package zalex14.shelter.entity.report;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The schedule for an adaptation period
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Pet's id
     */
    @Column(name = "pet_id")
    private Long petId;
    /**
     * Customer's id
     */
    @Column(name = "customer_id")
    private Long customerId;
}
