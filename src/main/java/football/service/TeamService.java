package football.service;

import football.models.entity.TeamEntity;

import java.io.IOException;

//ToDo - Implement all methods
public interface TeamService {
    boolean areImported();

    String readTeamsFileContent() throws IOException;

    String importTeams() throws IOException;

    TeamEntity findByName(String name);
}
