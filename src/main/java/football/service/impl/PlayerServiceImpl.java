package football.service.impl;

import football.models.dto.PlayerDto;
import football.models.dto.PlayerRootDto;
import football.models.entity.PlayerEntity;
import football.models.entity.StatEntity;
import football.models.entity.TeamEntity;
import football.models.entity.TownEntity;
import football.repository.PlayerRepository;
import football.service.PlayerService;
import football.service.StatService;
import football.service.TeamService;
import football.service.TownService;
import football.util.ValidationUtil;
import football.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Service
public class PlayerServiceImpl implements PlayerService {

    private final String XML_FILE = "src/main/resources/files/xml/players.xml";

    private final PlayerRepository playerRepository;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final TownService townService;
    private final TeamService teamService;
    private final StatService statService;

    public PlayerServiceImpl(PlayerRepository playerRepository, XmlParser xmlParser, ValidationUtil validationUtil, ModelMapper modelMapper, TownService townService, TeamService teamService, StatService statService) {
        this.playerRepository = playerRepository;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.townService = townService;
        this.teamService = teamService;
        this.statService = statService;
    }

    @Override
    public boolean areImported() {
        return playerRepository.count() > 0;
    }

    @Override
    public String readPlayersFileContent() throws IOException {
        return Files.readString(Path.of(XML_FILE));
    }

    @Override
    public String importPlayers() throws JAXBException {
        StringBuilder sb = new StringBuilder();
        PlayerRootDto playerRootDto = xmlParser.parseXml(PlayerRootDto.class, XML_FILE);

        for (PlayerDto playerDto : playerRootDto.getPlayerDtos()) {
            if (validationUtil.isValid(playerDto)) {
                TownEntity town = townService.findByName(playerDto.getTown().getName());
                TeamEntity team = teamService.findByName(playerDto.getTeam().getName());
                StatEntity stat = statService.findById(playerDto.getStat().getId());

                if (town == null || team == null || stat == null) {
                    sb.append("Invalid player").append(System.lineSeparator());
                    continue;
                }

                PlayerEntity player = modelMapper.map(playerDto, PlayerEntity.class);

                player.setBirthDate(LocalDate.parse(playerDto.getBirthDate()
                        , DateTimeFormatter.ofPattern("dd/MM/yyyy")));

                player.setTown(town);
                player.setTeam(team);
                player.setStat(stat);

                playerRepository.saveAndFlush(player);

                sb.append("Player").append(System.lineSeparator());
                continue;
            }
            System.out.println(validationUtil.violations(playerDto));
            sb.append("Invalid player").append(System.lineSeparator());
        }
        return sb.toString().trim();
    }

    @Override
    public String exportBestPlayers() {
        return null;
    }
}
