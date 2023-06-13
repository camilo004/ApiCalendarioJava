package calendario.calendario.servicios;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import calendario.calendario.interfaces.IFestivo;
import calendario.calendario.modelos.Festivo;
import calendario.calendario.modelos.Tipo;
import calendario.calendario.repositorios.RepositorioFestivo;
import calendario.calendario.repositorios.RepositorioTipo;

@Service
public class ServicioFestivo implements IFestivo {
    @Autowired
    private RepositorioFestivo repositorio;
    private RepositorioTipo repositorioTipo;

    public List<Tipo> obtenerTiposFestivo() {
        return repositorioTipo.findAll();
    }

    @Override
    public List<Festivo> listar() {
        return repositorio.findAll();
    }

    @Override
    public boolean esFestivo(int dia, int mes, int año) {
        // Obtener la lista de festivos de la base de datos
        List<Festivo> festivos = repositorio.findAll();

        // Calcular la fecha de Pascua para el año dado
        LocalDate fechaPascua = calcularFechaPascua(año);

        // Verificar si la fecha dada es festiva
        for (Festivo festivo : festivos) {
            LocalDate fechaFestivo = calcularFechaFestivo(festivo, fechaPascua);
            if (fechaFestivo.getDayOfMonth() == dia && fechaFestivo.getMonthValue() == mes) {
                return true;
            }
        }

        return false;
    }

    @Override
    public List<String> obtenerFechasFestivas(int año) {
        List<String> fechasFestivas = new ArrayList<>();

        // Obtener la lista de festivos de la base de datos
        List<Festivo> festivos = repositorio.findAll();

        // Calcular la fecha de Pascua para el año dado
        LocalDate fechaPascua = calcularFechaPascua(año);

        // Calcular la fecha de cada festivo
        for (Festivo festivo : festivos) {
            LocalDate fechaFestivo = calcularFechaFestivo(festivo, fechaPascua);
            String fechaFormateada = fechaFestivo.getDayOfMonth() + "/" + fechaFestivo.getMonthValue() + "/"
                    + fechaFestivo.getYear();
            fechasFestivas.add(fechaFormateada);
        }

        return fechasFestivas;
    }

    private LocalDate calcularFechaPascua(int año) {
        int a = año % 19;
        int b = año % 4;
        int c = año % 7;
        int d = (19 * a + 24) % 30;

        int dias = d + (2 * b + 4 * c + 6 * d + 5) % 7;

        LocalDate fechaMarzo = LocalDate.of(año, 3, 15); // 15 de marzo
        LocalDate fechaPascua = fechaMarzo.plusDays(dias);

        return fechaPascua;
    }

    private LocalDate calcularFechaFestivo(Festivo festivo, LocalDate fechaPascua) {
        LocalDate fechaFestivo;

        if (festivo.getTipo().getId() == 1) { // Festivo fijo
            fechaFestivo = LocalDate.of(fechaPascua.getYear(), festivo.getMes(), festivo.getDia());
        } else if (festivo.getTipo().getId() == 2) { // Ley de "Puente festivo"
            fechaFestivo = LocalDate.of(fechaPascua.getYear(), festivo.getMes(), festivo.getDia());
            fechaFestivo = trasladarAlSiguienteLunes(fechaFestivo);
        } else if (festivo.getTipo().getId() == 3) { // Basado en el domingo de pascua
            fechaFestivo = fechaPascua.plusDays(festivo.getDiasPascua());
        } else if (festivo.getTipo().getId() == 4) { // Basado en el domingo de pascua y Ley de "Puente festivo"
            fechaFestivo = fechaPascua.plusDays(festivo.getDiasPascua());
            fechaFestivo = trasladarAlSiguienteLunes(fechaFestivo);
        } else {
            throw new IllegalArgumentException("Tipo de festivo no válido");
        }

        return fechaFestivo;
    }

    private LocalDate trasladarAlSiguienteLunes(LocalDate fecha) {
        while (fecha.getDayOfWeek() != DayOfWeek.MONDAY) {
            fecha = fecha.plusDays(1);
        }
        return fecha;
    }

}
