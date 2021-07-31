package football.repository;

import football.models.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {
    @Query("SELECT p FROM PlayerEntity p" +
            " JOIN StatEntity s ON s.id = p.stat.id" +
            " WHERE p.birthDate BETWEEN :birthDate AND :birthDate2" +
            " ORDER BY s.shooting DESC, s.passing DESC, s.endurance DESC, p.lastName")
    List<PlayerEntity> findBestPlayers(LocalDate birthDate, LocalDate birthDate2);
}
