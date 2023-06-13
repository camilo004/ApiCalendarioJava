package calendario.calendario.interfaces;

import java.util.List;

import calendario.calendario.modelos.Festivo;

public interface IFestivo {
    public List<Festivo>listar();
    boolean esFestivo(int dia, int mes, int año);
     public List<String> obtenerFechasFestivas(int año);
}
