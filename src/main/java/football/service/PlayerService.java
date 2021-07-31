package football.service;

public interface PlayerService {
    boolean areImported();

    String readPlayersFileContent() ;

    String importPlayers() ;

    String exportBestPlayers();
}
