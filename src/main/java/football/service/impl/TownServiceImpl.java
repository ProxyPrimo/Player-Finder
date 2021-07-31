package football.service.impl;

import com.google.gson.Gson;
import football.models.dto.TownDto;
import football.models.entity.TownEntity;
import football.repository.TownRepository;
import football.service.TownService;
import football.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class TownServiceImpl implements TownService {

    public static final String TOWN_FILE = "src/main/resources/files/json/towns.json";

    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    public TownServiceImpl(TownRepository townRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(Path.of(TOWN_FILE));
    }

    @Override
    public String importTowns() throws IOException {
        StringBuilder sb = new StringBuilder();
        TownDto[] townDtos = gson.fromJson(readTownsFileContent(), TownDto[].class);
        for (TownDto townDto : townDtos) {
            if (!validationUtil.isValid(townDto)) {
                sb.append("Invalid Town").append(System.lineSeparator());
                continue;
            }

            TownEntity town = modelMapper.map(townDto, TownEntity.class);
            townRepository.saveAndFlush(town);
            sb.append("Valid Town").append(System.lineSeparator());
        }
        return sb.toString().trim();
    }

    @Override
    public TownEntity findByName(String name) {
        return townRepository.findByName(name);
    }
}
