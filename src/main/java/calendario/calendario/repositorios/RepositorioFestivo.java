package calendario.calendario.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import calendario.calendario.modelos.Festivo;

@Repository
public interface RepositorioFestivo extends JpaRepository <Festivo, Long> {
    List<Festivo> findAll();
    List<Festivo> findAllByDiaAndMes(int dia, int mes);
}
