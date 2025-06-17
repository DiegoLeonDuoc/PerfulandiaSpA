package PerfulandiaSpA.Servicio;

import PerfulandiaSpA.DTO.HorarioTrabajoDTO;
import PerfulandiaSpA.Entidades.Producto;
import PerfulandiaSpA.Entidades.HorarioTrabajo;

import PerfulandiaSpA.Entidades.Sucursal;
import PerfulandiaSpA.Repositorio.ProductoRepository;
import PerfulandiaSpA.Repositorio.HorarioTrabajoRepository;
import PerfulandiaSpA.Repositorio.SucursalRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HorarioTrabajoService {

    @Autowired
    HorarioTrabajoRepository horarioTrabajoRepository;

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    SucursalRepository sucursalRepository;

    public HorarioTrabajo saveHorarioTrabajo(HorarioTrabajoDTO horarioTrabajoDTO) {
        HorarioTrabajo horarioTrabajo = new HorarioTrabajo();
        horarioTrabajo.setDiaSemana(horarioTrabajoDTO.getDiaSemana());
        horarioTrabajo.setHorarioApertura(horarioTrabajoDTO.getHorarioApertura());
        horarioTrabajo.setHorarioCierre(horarioTrabajoDTO.getHorarioCierre());

        Optional<Sucursal> sucursal = sucursalRepository.findById(horarioTrabajoDTO.getIdSucursal());

        if (sucursal.isPresent()) {
            horarioTrabajo.setSucursal(sucursal.get());
            return horarioTrabajoRepository.save(horarioTrabajo);
        } else {
            throw new EntityNotFoundException("Sucursal no encontrada");
        }
    }

    public List<HorarioTrabajo> getHorariosTrabajo() {
        return horarioTrabajoRepository.findAll();
    }

    public Optional<HorarioTrabajo> getHorarioTrabajoByID(int id) {
        return horarioTrabajoRepository.findById(id);
    }

    public HorarioTrabajo updateHorarioTrabajo(Integer id, HorarioTrabajoDTO horarioTrabajoDTO) {
        HorarioTrabajo horarioTrabajoExistente = horarioTrabajoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("HorarioTrabajo no encontrado"));
        horarioTrabajoExistente.setDiaSemana(horarioTrabajoDTO.getDiaSemana());
        horarioTrabajoExistente.setHorarioApertura(horarioTrabajoDTO.getHorarioApertura());
        horarioTrabajoExistente.setHorarioCierre(horarioTrabajoDTO.getHorarioCierre());
        Optional<Sucursal> sucursal = sucursalRepository.findById(horarioTrabajoDTO.getIdSucursal());

        if (sucursal.isPresent()) {
            horarioTrabajoExistente.setId(id);
            horarioTrabajoExistente.setSucursal(sucursal.get());
        } else {
            throw new EntityNotFoundException("Sucursal no encontrada");
        }

        return horarioTrabajoRepository.save(horarioTrabajoExistente);
    }

    public void deleteHorarioTrabajo(int id) {
        horarioTrabajoRepository.deleteById(id);
    }

}
