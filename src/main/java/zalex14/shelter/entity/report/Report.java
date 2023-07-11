package zalex14.shelter.entity.report;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity: Report
 * The adopter daily report of the animal content, incl.
 * animal diet
 * animal well-being and health at new environment
 * animal change in behavior
 * actual animal photo
 */
@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "reports")
public class Report {
    /**
     * The report's id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * The report's date & time
     */
    @Column(name = "report_time")
    private LocalDateTime reportTime;

    /**
     * Pet's daily diet
     */
    @Column(name = "diet")
    private String diet;

    /**
     * Pet's daily health
     */
    @Column(name = "health")
    private String health;

    /**
     * Change at pet's behavior
     */
    @Column(name = "behavior")
    private String behavior;

    /**
     * The customer's id
     */
    @Column(name = "customer_id")
    private Long customerId;

    /**
     * Pet's id
     */
    @Column(name = "pet_id")
    private Long petId;

    /**
     * The daily pet's photo
     */
    @Column(name = "photo_id")
    private Long photoId;

}
