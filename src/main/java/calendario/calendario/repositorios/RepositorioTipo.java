package calendario.calendario.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import calendario.calendario.modelos.Tipo;

@Repository

public interface  RepositorioTipo extends JpaRepository<Tipo, Long> {

}
