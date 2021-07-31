package football.service.impl;

import com.google.gson.Gson;
import football.models.dto.TeamDto;
import football.models.entity.TeamEntity;
import football.models.entity.TownEntity;
import football.repository.TeamRepository;
import football.service.TeamService;
import football.service.TownService;
import football.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class TeamServiceImpl implements TeamService {

    private final String TEAMS_FILE = "src/main/resources/files/json/teams.json";

    private final TeamRepository teamRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final TownService townService;
    private final ModelMapper modelMapper;

    public TeamServiceImpl(TeamRepository teamRepository, Gson gson, ValidationUtil validationUtil, TownService townService, ModelMapper modelMapper) {
        this.teamRepository = teamRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.townService = townService;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return teamRepository.count() > 0;
    }

    @Override
    public String readTeamsFileContent() throws IOException {
        return Files.readString(Path.of(TEAMS_FILE));
    }

    @Override
    public String importTeams() throws IOException {
        StringBuilder sb = new StringBuilder();

        TeamDto[] teamDtos = gson.fromJson(readTeamsFileContent(), TeamDto[].class);

        for (TeamDto teamDto : teamDtos) {
            if (validationUtil.isValid(teamDto)) {
                TownEntity town = townService.findByName(teamDto.getTownName());
                if (town == null) {
                    sb.append("Invalid Team").append(System.lineSeparator());
                    continue;
                }
                TeamEntity team = modelMapper.map(teamDto, TeamEntity.class);
                team.setTown(town);
                teamRepository.saveAndFlush(team);
                sb.append("Team").append(System.lineSeparator());
            } else {
                sb.append("Invalid Team").append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }

    @Override
    public TeamEntity findByName(String name) {
        return teamRepository.findByName(name);
    }
}
