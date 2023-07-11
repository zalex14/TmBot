package zalex14.shelter.entity.pet;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 *  Entity: Pets
 */
@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "pets")
public class Pet {
    /**
     * The pet's id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OrderBy
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * The pet's nickname
     */
    @Column(name = "nick")
    private String nickName;

    /**
     * The pet's age
     */
    @Column(name = "age")
    private int age;

    /**
     * The pet's sickness
     */
    @Column(name = "sickness")
    private boolean isSickness;

    /**
     * The pet's description
     */
    @Column(name = "description")
    private String description;

    /**
     * The pet's breed: true for a dog / false for a cat
     */
    @Column(name = "breed")
    private boolean breed;

    /**
     * The pet's availability: true for available / false for dropped out
     */
    @Column(name = "available")
    private boolean isAvailable;

    /**
     * Duration, days up to finish of adaptation
     */
    @Column(name = "duration")
    private Integer duration;

    /**
     *  Stage of pet's living
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "schema")
    private Schema schema;

    public enum Schema {
        SEEKING,        // at the shelter, seeking for an adopter
        TRIAL30,        // found and transferred to an adopter, 30 days trial
        PASSED,         // successfully passed, conveyed
        EXTENSION14,    // additional 14 days trail
        EXTENSION30,    // additional 30 days trail
        REFUSED         // refused, return to the shelter
    }
}
