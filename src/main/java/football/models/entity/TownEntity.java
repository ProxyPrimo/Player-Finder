package football.models.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "towns")
public class TownEntity extends BaseEntity {
    private String name;
    private Integer population;
    private String travelGuide;

    public TownEntity() {
    }

    @Length(min = 2, message = "The name must be more than 2 characters long")
    @Column(unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Positive
    @Column
    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    @Length(min = 10, message = "The travel guide must be more than 10 characters long")
    @Column(columnDefinition = "TEXT")
    public String getTravelGuide() {
        return travelGuide;
    }

    public void setTravelGuide(String travelGuide) {
        this.travelGuide = travelGuide;
    }
}
