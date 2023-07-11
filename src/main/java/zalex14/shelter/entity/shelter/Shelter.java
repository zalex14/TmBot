package zalex14.shelter.entity.shelter;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Entity: Shelter
 * The shelter general description
 */
@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "shelters")
public class Shelter {
    /**
     * The shelter id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * The shelter telegram id for contacts
     */
    @Column(name = "telegram_id")
    private Long telegramId;

    /**
     * The shelter title
     */
    @Column(name = "title")
    private String title;

    /**
     * The shelter general information
     */
    @Column(name = "information")
    private String information;

    /**
     * The shelter address
     */
    @Column(name = "address")
    private String address;

    /**
     * The shelter location latitude
     */
    @Column(name = "latitude")
    private Float latitude;

    /**
     * The shelter location longitude
     */
    @Column(name = "longitude")
    private Float longitude;

    /**
     * The shelter contact's phone
     */
    @Column(name = "phone")
    private String phone;

    /**
     * Shelter's working hours
     */
    @Column(name = "working_hours")
    private String workingHours;

    /**
     * Rules of territory_admission
     */
    @Column(name = "territory_admission")
    private String territoryAdmission;

    /**
     * Rules for territory_staying
     */
    @Column(name = "territory_staying")
    private String territoryStaying;

    /**
     * Contacts of security personal for admission
     */
    @Column(name = "security_contacts")
    private String securityContacts;

    /**
     * pet's acquaintance
     */
    @Column(name = "pet_acquaintance")
    private String petAcquaintance;

    /**
     * document_list
     */
    @Column(name = "documents")
    private String documentList;

    /**
     * travel_recommendation
     */
    @Column(name = "travel_recommendation")
    private String travelRecommendation;

    /**
     * child_arranging
     */
    @Column(name = "child_arrangement")
    private String childArranging;

    /**
     * adult_arranging
     */
    @Column(name = "adult_arrangement")
    private String adultArranging;

    /**
     * sick_arrangement
     */
    @Column(name = "sick_arrangement")
    private String sickArrangement;

    /**
     * handler_advice
     */
    @Column(name = "handler_advice")
    private String handlerAdvice;

    /**
     * refusal_reason
     */
    @Column(name = "refusal_reason")
    private String refusalReason;
}
