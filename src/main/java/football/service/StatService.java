package football.service;

import football.models.entity.StatEntity;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface StatService {
    boolean areImported();

    String readStatsFileContent() throws IOException;

    String importStats() throws JAXBException;

    StatEntity findById(Long id);

}
