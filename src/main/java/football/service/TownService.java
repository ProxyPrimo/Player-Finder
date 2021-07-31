package football.service;

import football.models.entity.TownEntity;

import java.io.IOException;

public interface TownService {
    boolean areImported();

    String readTownsFileContent() throws IOException;

    String importTowns() throws IOException;

    TownEntity findByName(String name);
}
