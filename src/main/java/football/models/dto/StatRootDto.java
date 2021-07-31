package football.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "stats")
@XmlAccessorType(XmlAccessType.FIELD)
public class StatRootDto {

    @XmlElement(name = "stat")
    private List<StatDto> statDtoList;

    public StatRootDto() {
    }

    public List<StatDto> getStatDtoList() {
        return statDtoList;
    }

    public void setStatDtoList(List<StatDto> stats) {
        this.statDtoList = stats;
    }
}
