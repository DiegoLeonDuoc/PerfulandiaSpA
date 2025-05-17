package PerfulandiaSpA.Servicio;

import PerfulandiaSpA.Entidades.HorarioTrabajo;
import PerfulandiaSpA.Repositorio.HorarioTrabajoRepository;
import PerfulandiaSpA.Repositorio.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HorarioTrabajoService {


    @Autowired
    HorarioTrabajoRepository horarioTrabajoRepository;

    // Inyección del repositorio de sucursales (necesaria para asociar la sucursal al horario)
    @Autowired
     SucursalRepository sucursalRepository;

    // MÉTODO CREATE
    // Guardar new horario de trabajo
    public String saveHorarioTrabajo(HorarioTrabajo horario, int id_sucursal) {
        if (sucursalRepository.existsById(id_sucursal)){
            horario.setSucursal(sucursalRepository.findById(id_sucursal).get());
            horarioTrabajoRepository.save(horario); // Guardao
            return "Horario de trabajo agregado con éxito";
        } else {
            return "La sucursal no existe";
        }
    }

    // MÉTODO DELETE
    // Elimina horario de trabajo por ID
    public String deleteHorarioTrabajo(int id) {
        if (horarioTrabajoRepository.existsById(id)) {
            horarioTrabajoRepository.deleteById(id);
            return "Horario de trabajo eliminado con éxito";
        }
        return "Horario de trabajo no encontrado";
    }

    // MÉTODO UPDATE
    // Actualizar horario de trabajo por ID
    public String updateHorarioTrabajo(HorarioTrabajo horario, int id_sucursal) {
        if (horarioTrabajoRepository.existsById(horario.getId())) {
            horario.setSucursal(sucursalRepository.getReferenceById(id_sucursal)); //posiciona en bd antes de la actualizacion
            horarioTrabajoRepository.save(horario);
            return "Horario de trabajo actualizado con éxito";
        }
        return "Horario de trabajo no encontrado";
    }

    // MÉTODO READ (LISTAR TODOS en ToString)
    public String getHorariosTrabajo() {
        String output = "";
        for (HorarioTrabajo horario : horarioTrabajoRepository.findAll()) {
            output = datosHorario(output, horario); // Formateo d datos
        }

        if (output.isEmpty()) {
            return "No hay horarios de trabajo registrados";
        } else {
            return output;
        }
    }

    // MÉTODO READ (LISTAR TODOS EN JSON)
    // Ideal para REST (retorna lista cruda/paraOcupar)
    public List<HorarioTrabajo> getHorariosTrabajoJSON() {
        return horarioTrabajoRepository.findAll();
    }

    // MÉTODO READ (BUSCAR POR ID)
    public String getHorarioTrabajoById(int id) {
        if (horarioTrabajoRepository.existsById(id)) {
            HorarioTrabajo horario = horarioTrabajoRepository.findById(id).get();
            return datosHorario("", horario); // Retorna los datos formateados
        }
        return "Horario de trabajo no encontrado";
    }

    // MÉTODO toString/formateo de datos
    private String datosHorario(String output, HorarioTrabajo horario) {
        int minutoApertura = horario.getHorarioApertura().getMinute();
        String minutoAperturaFormateado = String.format("%02d", minutoApertura);
        int minutoCierre = horario.getHorarioCierre().getMinute();
        String minutoCierreFormateado = String.format("%02d", minutoCierre);

        output += "ID Horario: " + horario.getId() + "\n";
        output += "Día semana: " + HorarioTrabajo.diasSemana.get(horario.getDiaSemana()) + "\n";
        output += "Hora apertura: " + horario.getHorarioApertura() + "\n";
        output += "Hora cierre: " + horario.getHorarioCierre() + "\n";
        output += "ID Sucursal: " + horario.getSucursal().getId() + "\n";
        output += "Nombre Sucursal: " + horario.getSucursal().getNombreSucursal() + "\n";
        output += "Dirección Sucursal: " + horario.getSucursal().getDireccionSucursal() + "\n";
        output += "\n";
        return output;
    }
}