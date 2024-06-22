package model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "film", schema = "public")
public class Film implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id", unique = true, nullable = false)
    private int filmId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "release_year")
    private Integer releaseYear;

    @Column(name = "rental_duration", nullable = false)
    private short rentalDuration;

    @Column(name = "rental_rate", nullable = false, precision = 4)
    private BigDecimal rentalRate;

    @Column(name = "length")
    private Short length;

    @Column(name = "replacement_cost", nullable = false, precision = 5)
    private BigDecimal replacementCost;

    @Column(name = "rating")
    private String rating;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_update", nullable = false)
    private Date lastUpdate;

    @Column(name = "special_features", columnDefinition = "TEXT")
    private String specialFeatures;

    @Column(name = "fulltext", columnDefinition = "TEXT")
    private String fulltext;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "film")
    private Set<Inventory> inventories = new HashSet<>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "film")
    private Set<FilmCategory> filmCategories = new HashSet<>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "film")
    private Set<FilmActor> filmActors = new HashSet<>(0);

    // Default constructor
    public Film() {
    }

    // Constructor with most fields
    public Film(int filmId, Language language, String title, short rentalDuration, BigDecimal rentalRate, BigDecimal replacementCost, Date lastUpdate, String fulltext) {
        this.filmId = filmId;
        this.language = language;
        this.title = title;
        this.rentalDuration = rentalDuration;
        this.rentalRate = rentalRate;
        this.replacementCost = replacementCost;
        this.lastUpdate = lastUpdate;
        this.fulltext = fulltext;
    }

    // Getters and Setters
    public int getFilmId() { return filmId; }
    public void setFilmId(int filmId) { this.filmId = filmId; }

    public Language getLanguage() { return language; }
    public void setLanguage(Language language) { this.language = language; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getReleaseYear() { return releaseYear; }
    public void setReleaseYear(Integer releaseYear) { this.releaseYear = releaseYear; }

    public short getRentalDuration() { return rentalDuration; }
    public void setRentalDuration(short rentalDuration) { this.rentalDuration = rentalDuration; }

    public BigDecimal getRentalRate() { return rentalRate; }
    public void setRentalRate(BigDecimal rentalRate) { this.rentalRate = rentalRate; }

    public Short getLength() { return length; }
    public void setLength(Short length) { this.length = length; }

    public BigDecimal getReplacementCost() { return replacementCost; }
    public void setReplacementCost(BigDecimal replacementCost) { this.replacementCost = replacementCost; }

    public String getRating() { return rating; }
    public void setRating(String rating) { this.rating = rating; }

    public Date getLastUpdate() { return lastUpdate; }
    public void setLastUpdate(Date lastUpdate) { this.lastUpdate = lastUpdate; }

    public String getSpecialFeatures() { return specialFeatures; }
    public void setSpecialFeatures(String specialFeatures) { this.specialFeatures = specialFeatures; }

    public String getFulltext() { return fulltext; }
    public void setFulltext(String fulltext) { this.fulltext = fulltext; }

    public Set<Inventory> getInventories() { return inventories; }
    public void setInventories(Set<Inventory> inventories) { this.inventories = inventories; }

    public Set<FilmCategory> getFilmCategories() { return filmCategories; }
    public void setFilmCategories(Set<FilmCategory> filmCategories) { this.filmCategories = filmCategories; }

    public Set<FilmActor> getFilmActors() { return filmActors; }
    public void setFilmActors(Set<FilmActor> filmActors) { this.filmActors = filmActors; }
}
