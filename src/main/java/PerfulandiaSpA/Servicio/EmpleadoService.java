package PerfulandiaSpA.Servicio;

import PerfulandiaSpA.DTO.EmpleadoDTO;
import PerfulandiaSpA.Entidades.Empleado;
import PerfulandiaSpA.Entidades.Empleado;
import PerfulandiaSpA.Entidades.Envio;
import PerfulandiaSpA.Entidades.Sucursal;
import PerfulandiaSpA.Repositorio.EmpleadoRepository;
import PerfulandiaSpA.Repositorio.SucursalRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoService {
    @Autowired
    EmpleadoRepository empleadoRepository;

    @Autowired
    SucursalRepository sucursalRepository;

    // C
    public Empleado crearEmpleado(EmpleadoDTO empleado) {
        Empleado newEmpleado = new Empleado();
        newEmpleado.setRutUsuario(empleado.getRutUsuario());
        newEmpleado.setDvUsuario(empleado.getDvUsuario());
        newEmpleado.setNomUsuario(empleado.getNomUsuario());
        newEmpleado.setNom2Usuario(empleado.getNom2Usuario());
        newEmpleado.setApellidoPaterno(empleado.getApellidoPaterno());
        newEmpleado.setApellidoMaterno(empleado.getApellidoMaterno());
        newEmpleado.setSexoUsuario(empleado.getSexoUsuario());
        newEmpleado.setDirUsuario(empleado.getDirUsuario());
        newEmpleado.setFechaNacimiento(empleado.getFechaNacimiento());
        newEmpleado.setTelefonoUsuario(empleado.getTelefonoUsuario());
        newEmpleado.setTel2Usuario(empleado.getTel2Usuario());
        newEmpleado.setEmailUsuario(empleado.getEmailUsuario());
        newEmpleado.setTipoUsuario("EMPLEADO");
        newEmpleado.setPassUsuario(new BCryptPasswordEncoder(10).encode(empleado.getPassUsuario()));

        Optional<Sucursal> sucursal = sucursalRepository.findById(empleado.getIdSucursal());

        if (sucursal.isPresent()) {
            newEmpleado.setSucursalAsociada(sucursal.get());
            return empleadoRepository.save(newEmpleado);
        } else {
            throw new EntityNotFoundException("Sucursal no encontrada");
        }
    }

    // R
    public List<Empleado> getEmpleados() {
        return empleadoRepository.findAll();
    }

    public List<Empleado> getEmpleadosBySucursal(Integer id) {
        List<Empleado> empleadosSucursal = new ArrayList<>();
        if (sucursalRepository.existsById(id)) {
            for (Empleado empleado : empleadoRepository.findAll()) {
                if (empleado.getSucursalAsociada().getId().equals(id)) {
                    empleadosSucursal.add(empleado);
                }
            }
            return empleadosSucursal;
        } else {
            throw new EntityNotFoundException("Sucursal no encontrada");
        }
    }

    public Optional<Empleado> getEmpleadoByRut(Integer rut) {
        return empleadoRepository.findById(rut);
    }

    // U

    public Empleado updateEmpleado(EmpleadoDTO empleado, int rut) {
        Empleado empleadoExistente = empleadoRepository.findById(rut)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado"));

        empleadoExistente.setRutUsuario(rut);
        empleadoExistente.setNomUsuario(empleado.getNomUsuario());
        empleadoExistente.setNom2Usuario(empleado.getNom2Usuario());
        empleadoExistente.setApellidoPaterno(empleado.getApellidoPaterno());
        empleadoExistente.setApellidoMaterno(empleado.getApellidoMaterno());
        empleadoExistente.setSexoUsuario(empleado.getSexoUsuario());
        empleadoExistente.setDirUsuario(empleado.getDirUsuario());
        empleadoExistente.setFechaNacimiento(empleado.getFechaNacimiento());
        empleadoExistente.setTelefonoUsuario(empleado.getTelefonoUsuario());
        empleadoExistente.setTel2Usuario(empleado.getTel2Usuario());
        empleadoExistente.setEmailUsuario(empleado.getEmailUsuario());
        String newPass = new BCryptPasswordEncoder(10).encode(empleado.getPassUsuario());
        empleadoExistente.setPassUsuario(newPass);
        empleadoExistente.setTipoUsuario("EMPLEADO");

        Optional<Sucursal> sucursal = sucursalRepository.findById(empleado.getIdSucursal());

        if (sucursal.isPresent()) {
            empleadoExistente.setSucursalAsociada(sucursal.get());
            return empleadoRepository.save(empleadoExistente);
        } else {
            throw new EntityNotFoundException("Sucursal no encontrada");
        }
    }

    // U/P
    public Empleado patchEmpleado(EmpleadoDTO empleado, int rut) {
        Empleado empleadoExistente = empleadoRepository.findById(rut)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado"));

        if (empleado.getNomUsuario() != null) {
            empleadoExistente.setNomUsuario(empleado.getNomUsuario());
        }
        if (empleado.getNom2Usuario() != null) {
            empleadoExistente.setNom2Usuario(empleado.getNom2Usuario());
        }
        if (empleado.getApellidoPaterno() != null) {
            empleadoExistente.setApellidoPaterno(empleado.getApellidoPaterno());
        }
        if (empleado.getApellidoMaterno() != null) {
            empleadoExistente.setApellidoMaterno(empleado.getApellidoMaterno());
        }
        if (empleado.getSexoUsuario() != null) {
            empleadoExistente.setSexoUsuario(empleado.getSexoUsuario());
        }
        if (empleado.getDirUsuario() != null) {
            empleadoExistente.setDirUsuario(empleado.getDirUsuario());
        }
        if (empleado.getFechaNacimiento() != null) {
            empleadoExistente.setFechaNacimiento(empleado.getFechaNacimiento());
        }
        if (empleado.getTelefonoUsuario() != null) {
            empleadoExistente.setTelefonoUsuario(empleado.getTelefonoUsuario());
        }
        if (empleado.getTel2Usuario() != null) {
            empleadoExistente.setTel2Usuario(empleado.getTel2Usuario());
        }
        if (empleado.getEmailUsuario() != null) {
            empleadoExistente.setEmailUsuario(empleado.getEmailUsuario());
        }
        if (empleado.getPassUsuario() != null) {
            String newPass = new BCryptPasswordEncoder(10).encode(empleado.getPassUsuario());
            empleadoExistente.setPassUsuario(newPass);
        }

        if (empleado.getIdSucursal() != null) {
            Optional<Sucursal> sucursal = sucursalRepository.findById(empleado.getIdSucursal());
            if (sucursal.isPresent()) {
                empleadoExistente.setSucursalAsociada(sucursal.get());
            } else {
                throw new EntityNotFoundException("Sucursal no encontrada");
            }
        }

        return empleadoRepository.save(empleadoExistente);
    }

    // D

    public void deleteEmpleado(int rut) {
        empleadoRepository.deleteById(rut);
    }
}
