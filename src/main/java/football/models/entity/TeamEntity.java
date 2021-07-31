package football.models.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;

@Entity
@Table(name = "teams")
public class TeamEntity extends BaseEntity {
    private String name;
    private String stadiumName;
    private Integer fanBase;
    private String history;
    private TownEntity town;

    public TeamEntity() {
    }

    @Length(min = 3, message = "The name must be at least 3 characters long")
    @Column(unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min = 3, message = "The stadium name must be at least 3 characters long")
    @Column(name = "stadium_name")
    public String getStadiumName() {
        return stadiumName;
    }

    public void setStadiumName(String stadiumName) {
        this.stadiumName = stadiumName;
    }

    @Min(1000)
    @Column(name = "fan_base")
    public Integer getFanBase() {
        return fanBase;
    }

    public void setFanBase(Integer fanBase) {
        this.fanBase = fanBase;
    }

    @Length(min = 10, message = "The history value needs to be at least 10 characters long")
    @Column(columnDefinition = "TEXT")
    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    @ManyToOne
    public TownEntity getTown() {
        return town;
    }

    public void setTown(TownEntity town) {
        this.town = town;
    }
}
