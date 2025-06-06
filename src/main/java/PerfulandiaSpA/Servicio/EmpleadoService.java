package PerfulandiaSpA.Servicio;

import PerfulandiaSpA.DTO.EmpleadoDTO;
import PerfulandiaSpA.Entidades.Empleado;
import PerfulandiaSpA.Entidades.Sucursal;
import PerfulandiaSpA.Repositorio.EmpleadoRepository;
import PerfulandiaSpA.Repositorio.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmpleadoService {
    @Autowired
    EmpleadoRepository empleadoRepository;

    @Autowired
    SucursalRepository sucursalRepository;

    // C
    public String crearEmpleado(EmpleadoDTO empleado) {
        if (empleadoRepository.existsById(empleado.getRutUsuario())) {
            return "Ya existe un usuario con ese rut";
        }
        if (!sucursalRepository.existsById(empleado.getIdSucursal())) {
            return "La sucursal no existe";
        }
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
        newEmpleado.setSucursalAsociada(sucursalRepository.findById(empleado.getIdSucursal()).get());
        empleadoRepository.save(newEmpleado);
        return "Empleado agregado con éxito";
    }

    // R
    public String getEmpleados() {
        String output = "";
        for (Empleado empleado : empleadoRepository.findAll()) {
            output = datosEmpleado(output, empleado);
        }
        if (output.isEmpty()) {
            return "No hay empleados registrados";
        } else {
            return output;
        }
    }

    public String getEmpleadoByRut(int id) {
        String output = "";
        if (empleadoRepository.existsById(id)) {
            Empleado empleado = empleadoRepository.findById(id).get();
            output = datosEmpleado(output, empleado);
            return output;
        }else{
            return "Empleado no encontrado";
        }
    }

    public List<Empleado> getEmpleadosJSON() {
        return empleadoRepository.findAll();
    }

    public String getEmpleadosSucursal(int id_sucursal) {
        String output = "";
        for (Empleado empleado : empleadoRepository.findAll()) {
            if (empleado.getSucursalAsociada().getId() == id_sucursal) {
                output = datosEmpleado(output, empleado);
            }
        }
        if (output.isEmpty()) {
            return "No hay empleados registrados";
        } else {
            return output;
        }
    }

    public List<Empleado> getEmpleadosSucursalJSON(int id_sucursal) {
        List<Empleado> empleadosSucursal = new ArrayList<>();
        for (Empleado empleado : empleadoRepository.findAll()) {
            if (empleado.getSucursalAsociada().getId() == id_sucursal) {
                empleadosSucursal.add(empleado);
            }
        }
        return empleadosSucursal;
    }

    // U

    public String updateEmpleado(EmpleadoDTO empleado, int rut) {
        if (empleadoRepository.existsById(rut)) {
            Empleado empleadoUpdate = empleadoRepository.findById(rut).get();
            empleadoUpdate.setNomUsuario(empleado.getNomUsuario());
            empleadoUpdate.setNom2Usuario(empleado.getNom2Usuario());
            empleadoUpdate.setApellidoPaterno(empleado.getApellidoPaterno());
            empleadoUpdate.setApellidoMaterno(empleado.getApellidoMaterno());
            empleadoUpdate.setSexoUsuario(empleado.getSexoUsuario());
            empleadoUpdate.setDirUsuario(empleado.getDirUsuario());
            empleadoUpdate.setFechaNacimiento(empleado.getFechaNacimiento());
            empleadoUpdate.setTelefonoUsuario(empleado.getTelefonoUsuario());
            empleadoUpdate.setTel2Usuario(empleado.getTel2Usuario());
            empleadoUpdate.setEmailUsuario(empleado.getEmailUsuario());
            empleadoUpdate.setSucursalAsociada(sucursalRepository.findById(empleado.getIdSucursal()).get());
            String newPass = new BCryptPasswordEncoder(10).encode(empleado.getPassUsuario());
            empleadoUpdate.setPassUsuario(newPass);
            empleadoRepository.save(empleadoUpdate);
            return "Empleado actualizado con éxito";
        } else {
            return "Empleado no encontrado";
        }
    }

    // U/P
    public String parcharEmpleado(EmpleadoDTO empleado, int rut) {
        if (empleadoRepository.existsById(rut)) {
            Empleado empleadoParchado = empleadoRepository.findById(rut).get();
            if (empleado.getNomUsuario() != null) {
                empleadoParchado.setNomUsuario(empleado.getNomUsuario());
            }
            if (empleado.getNom2Usuario() != null) {
                empleadoParchado.setNom2Usuario(empleado.getNom2Usuario());
            }
            if (empleado.getApellidoPaterno() != null) {
                empleadoParchado.setApellidoPaterno(empleado.getApellidoPaterno());
            }
            if (empleado.getApellidoMaterno() != null) {
                empleadoParchado.setApellidoMaterno(empleado.getApellidoMaterno());
            }
            if (empleado.getSexoUsuario() == 'M' || empleado.getSexoUsuario() == 'F') {
                empleadoParchado.setSexoUsuario(empleado.getSexoUsuario());
            }
            if (empleado.getDirUsuario() != null) {
                empleadoParchado.setDirUsuario(empleado.getDirUsuario());
            }
            if (empleado.getFechaNacimiento() != null) {
                empleadoParchado.setFechaNacimiento(empleado.getFechaNacimiento());
            }
            if (empleado.getTelefonoUsuario() != null) {
                empleadoParchado.setTelefonoUsuario(empleado.getTelefonoUsuario());
            }
            if (empleado.getTel2Usuario() != null) {
                empleadoParchado.setTel2Usuario(empleado.getTel2Usuario());
            }
            if (empleado.getEmailUsuario() != null) {
                empleadoParchado.setEmailUsuario(empleado.getEmailUsuario());
            }
            if (empleado.getPassUsuario() != null) {
                String newPass = new BCryptPasswordEncoder(10).encode(empleado.getPassUsuario());
                empleadoParchado.setPassUsuario(newPass);
            }
            if (empleado.getIdSucursal() != null) {
                Sucursal newSucursal = sucursalRepository.findById(empleado.getIdSucursal()).get();
                empleadoParchado.setSucursalAsociada(newSucursal);
            }
            empleadoRepository.save(empleadoParchado);
            return "Empleado actualizado con éxito";
        }
        return "Empleado no encontrado";
    }

    // D

    public String deleteEmpleado(int rut) {
        for (Empleado empleado : empleadoRepository.findAll()) {
            if (empleado.getRutUsuario() == rut) {
                empleadoRepository.delete(empleado);
                return "Empleado eliminado con éxito";
            }
        }
        return "Empleado no existente";
    }

    // Funciones no CRUD

    private String datosEmpleado(String output, Empleado empleado) {
        output += "RUT: " + empleado.getRutUsuario() + "-" + empleado.getDvUsuario() + "\n";
        output += "Nombre completo: " + empleado.getNomUsuario() + " ";
        if (empleado.getNom2Usuario() != null) {
            output += empleado.getNom2Usuario() + " ";
        }
        output += empleado.getApellidoPaterno();
        if (empleado.getNom2Usuario() != null) {
            output += " " + empleado.getApellidoMaterno() + "\n";
        } else {
            output += "\n";
        }
        output += "Sexo: " + empleado.getSexoUsuario() + "\n";
        output += "Fecha de nacimiento: " + empleado.getFechaNacimiento().toString() + "\n";
        output += "Dirección: " + empleado.getDirUsuario() + "\n";
        output += "Número de teléfono: +56" + empleado.getTelefonoUsuario() + "\n";
        if (empleado.getTel2Usuario() != null) {
            output += "Teléfono extra: +56" + empleado.getTel2Usuario() + "\n";
        }
        output += "Email: " + empleado.getEmailUsuario() + "\n";
        if (empleado.getSucursalAsociada() != null) {
            output += "Nombre Sucursal: " + empleado.getSucursalAsociada().getNombreSucursal() + "\n";
            output += "Dirección sucursal: " + empleado.getSucursalAsociada().getDireccionSucursal() + "\n";
        } else {
            output += "Nombre Sucursal: Este empleado no posee una sucursal asociada\n";
        }

        output += "\n";
        return output;
    }
}
