package football.models.dto;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Positive;

public class TownDto {
    @Expose
    private String name;

    @Expose
    private Integer population;

    @Expose
    private String travelGuide;


    public TownDto() {
    }

    @Length(min = 2, message = "The name must be more than 2 characters long")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Positive
    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    @Length(min = 10, message = "The travel guide must be more than 10 characters long")
    public String getTravelGuide() {
        return travelGuide;
    }

    public void setTravelGuide(String travelGuide) {
        this.travelGuide = travelGuide;
    }
}
