package football.service;

import java.io.IOException;

public interface PlayerService {
    boolean areImported();
    String readPlayersFileContent() throws IOException;
    String importPlayers() throws IOException;
    String exportBestPlayers();
}
