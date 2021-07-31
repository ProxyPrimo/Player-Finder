package football.service.impl;

import football.models.dto.StatDto;
import football.models.dto.StatRootDto;
import football.models.entity.StatEntity;
import football.repository.StatRepository;
import football.service.StatService;
import football.util.ValidationUtil;
import football.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class StatServiceImpl implements StatService {

    private final String XML_FILE = "src/main/resources/files/xml/stats.xml";

    private final StatRepository statRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;

    public StatServiceImpl(StatRepository statRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidationUtil validationUtil) {
        this.statRepository = statRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return statRepository.count() > 0;
    }

    @Override
    public String readStatsFileContent() throws IOException {
        return Files.readString(Path.of(XML_FILE));
    }

    @Override
    public String importStats() throws JAXBException {
        StringBuilder sb = new StringBuilder();

        StatRootDto statRootDto = xmlParser.parseXml(StatRootDto.class, XML_FILE);
        for (StatDto statDto : statRootDto.getStatDtoList()) {
            if (validationUtil.isValid(statDto)) {
                StatEntity stat = modelMapper.map(statDto, StatEntity.class);
                sb.append("Stat");
                statRepository.saveAndFlush(stat);
            } else {
                sb.append("Invalid Stat");
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
