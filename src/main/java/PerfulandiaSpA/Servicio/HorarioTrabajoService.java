package PerfulandiaSpA.Servicio;

import PerfulandiaSpA.Entidades.HorarioTrabajo;
import PerfulandiaSpA.Repositorio.HorarioTrabajoRepository;
import PerfulandiaSpA.Repositorio.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Declara esta clase como un servicio de Spring (componente de lógica de negocio)
public class HorarioTrabajoService {

    // Inyección del repositorio de horarios
    @Autowired
    HorarioTrabajoRepository horarioTrabajoRepository;

    // Inyección del repositorio de sucursales (necesaria para asociar la sucursal al horario)
    @Autowired
    SucursalRepository sucursalRepository;

    // MÉTODO CREATE
    // Guarda un nuevo horario de trabajo
    public String saveHorarioTrabajo(HorarioTrabajo horario) {
        horarioTrabajoRepository.save(horario); // Guarda el horario
        return "Horario de trabajo agregado con éxito";
    }

    // MÉTODO DELETE
    // Elimina un horario de trabajo por su ID
    public String deleteHorarioTrabajo(Integer id) {
        if (horarioTrabajoRepository.existsById(id)) {
            horarioTrabajoRepository.deleteById(id);
            return "Horario de trabajo eliminado con éxito";
        }
        return "Horario de trabajo no encontrado";
    }

    // MÉTODO UPDATE
    // Actualiza un horario de trabajo si existe su ID
    public String updateHorarioTrabajo(HorarioTrabajo horario, Integer id) {
        if (horarioTrabajoRepository.existsById(id)) {
            horario.setId(id); // Asegura que el ID coincida
            horarioTrabajoRepository.save(horario); // Realiza el update
            return "Horario de trabajo actualizado con éxito";
        }
        return "Horario de trabajo no encontrado";
    }

    // MÉTODO READ (LISTAR TODOS)
    // Devuelve todos los horarios de trabajo como texto formateado
    public String getHorariosTrabajo() {
        String output = "";
        for (HorarioTrabajo horario : horarioTrabajoRepository.findAll()) {
            output = datosHorario(output, horario); // Formatea los datos
        }

        if (output.isEmpty()) {
            return "No hay horarios de trabajo registrados";
        } else {
            return output;
        }
    }

    // MÉTODO READ (LISTAR TODOS EN JSON)
    // Ideal para controladores REST (retorna lista cruda)
    public List<HorarioTrabajo> getHorariosTrabajoJSON() {
        return horarioTrabajoRepository.findAll();
    }

    // MÉTODO READ (BUSCAR POR ID)
    public String getHorarioTrabajoById(Integer id) {
        if (horarioTrabajoRepository.existsById(id)) {
            HorarioTrabajo horario = horarioTrabajoRepository.findById(id).get();
            return datosHorario("", horario); // Retorna los datos formateados
        }
        return "Horario de trabajo no encontrado";
    }

    // MÉTODO AUXILIAR PARA FORMATEO DE SALIDA
    private String datosHorario(String output, HorarioTrabajo horario) {
        output += "ID Horario: " + horario.getId() + "\n";
        output += "Día semana: " + (horario.getDiaSemana() ? "Sí" : "No") + "\n";
        output += "Hora apertura: " + horario.getHorarioApertura() + "\n";
        output += "Hora cierre: " + horario.getHorarioCierre() + "\n";
        output += "ID Sucursal: " + horario.getIdSucursal().getId() + "\n";
        output += "Nombre Sucursal: " + horario.getIdSucursal().getNombreSucursal() + "\n";
        output += "Dirección Sucursal: " + horario.getIdSucursal().getDireccionSucursal() + "\n";
        output += "\n";
        return output;
    }
}