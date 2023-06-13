package calendario.calendario.controladores;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import calendario.calendario.interfaces.IFestivo;
import calendario.calendario.modelos.Festivo;


@RestController
@RequestMapping("/festivos")
public class FestivoControlador {
    @Autowired
    private IFestivo servicio;
    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    public List<Festivo> listar() {
        return servicio.listar();
    }
   @GetMapping("/validar/{dia}/{mes}/{año}")
    public String validarFechaFestiva(@PathVariable int dia, @PathVariable int mes, @PathVariable int año) {
        try {
            LocalDate fecha = LocalDate.of(año, mes, dia);
            boolean esFestivo = servicio.esFestivo(dia, mes, año);
            if (esFestivo) {
                return "La fecha " + dia + "/" + mes + "/" + año + " es festiva";
            } else {
                return "La fecha " + dia + "/" + mes + "/" + año + " no es festiva";
            }
        } catch (DateTimeException e) {
            return "La fecha " + dia + "/" + mes + "/" + año + " no es válida";
        }
    }


    @GetMapping("/todos/{año}")
    public List<String> obtenerFechasFestivas(@PathVariable int año) {
        return servicio.obtenerFechasFestivas(año);
    }

}


    
    
  

    

  

