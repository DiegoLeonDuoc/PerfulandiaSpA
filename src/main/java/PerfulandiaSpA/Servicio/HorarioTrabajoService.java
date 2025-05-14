package PerfulandiaSpA.Servicio;

import PerfulandiaSpA.Entidades.HorarioTrabajo;
import PerfulandiaSpA.Repositorio.HorarioTrabajoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HorarioTrabajoService {

    @Autowired
    HorarioTrabajoRepository horarioTrabajoRepository;

    // Crear o actualizar un horario de trabajo
    public HorarioTrabajo guardarHorario(HorarioTrabajo horarioTrabajo) {
        return horarioTrabajoRepository.save(horarioTrabajo);
    }

    // Obtener todos los horarios de trabajo
    public List<HorarioTrabajo> obtenerHorarios() {
        return horarioTrabajoRepository.findAll();
    }

    // Obtener un horario de trabajo por su ID
    public Optional<HorarioTrabajo> obtenerPorId(Long id) {
        return horarioTrabajoRepository.findById(id);
    }

    // Eliminar un horario de trabajo por su ID
    public void eliminarHorario(Long id) {
        horarioTrabajoRepository.deleteById(id);
    }
}
