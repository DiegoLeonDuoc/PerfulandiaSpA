package PerfulandiaSpA.Servicio;

import PerfulandiaSpA.Entidades.Empleado;
import PerfulandiaSpA.Repositorio.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoService {
    
    @Autowired
    EmpleadoRepository empleadoRepository;

    public String saveEmpleado(Empleado empleado) {
        empleadoRepository.save(empleado);
        return "Empleado agregado con éxito";
    }

    public String deleteEmpleado(int id) {
        empleadoRepository.deleteById(id);
        return "Empleado eliminado con éxito";
    }

    public String updateEmpleado(Empleado empleado, int id) {
        if (empleadoRepository.existsById(id)) {
            empleadoRepository.save(empleado);
            return "Empleado actualizado con éxito";
        }
        return "Empleado no encontrado";
    }

    public String getEmpleados() {
        String output = "";
        for (Empleado empleado : empleadoRepository.findAll()) {
            output = datosEmpleado(output, empleado);
        }

        if (output.isEmpty()) {
            //return empleadoRepository.findAll().toString();
            return "No hay empleados registrados";
        } else {
            return output;
        }
    }

    public List<Empleado> getEmpleadosJSON() {
        return empleadoRepository.findAll();
    }

    private String datosEmpleado(String output, Empleado empleado) {
        output+="RUT: "+empleado.getUsuarioAsociado().getRutUsuario()+"-"+empleado.getUsuarioAsociado().getDvUsuario()+"\n";
        output+="Nombre completo: "+empleado.getUsuarioAsociado().getNomUsuario()+" ";
        if (empleado.getUsuarioAsociado().getNom2Usuario() != null) {
            output+=empleado.getUsuarioAsociado().getNom2Usuario()+" ";
        }
        output+=empleado.getUsuarioAsociado().getApellidoPaterno();
        if (empleado.getUsuarioAsociado().getApellidoMaterno() != null) {
            output+=" "+empleado.getUsuarioAsociado().getApellidoMaterno()+"\n";
        } else {
            output+="\n";
        }
        output+="Sexo: "+empleado.getUsuarioAsociado().getSexoUsuario()+"\n";
        output+="Fecha de nacimiento: "+empleado.getUsuarioAsociado().getFechaNacimiento().toString()+"\n";
        output+="Dirección: "+empleado.getUsuarioAsociado().getDirUsuario()+"\n";
        output+="Número de teléfono: +569"+empleado.getUsuarioAsociado().getTelefonoUsuario()+"\n";
        if (empleado.getUsuarioAsociado().getTel2Usuario() != null) {
            output+="Teléfono extra: +569"+empleado.getUsuarioAsociado().getTel2Usuario()+"\n";
        }
        output+="Email: "+empleado.getUsuarioAsociado().getEmailUsuario()+"\n";
        output+="ID Sucursal: "+empleado.getSucursalAsociada().getId()+"\n";
        output+="Nombre Sucursal: "+empleado.getSucursalAsociada().getNombreSucursal()+"\n";
        output+="Dirección Sucursal: "+empleado.getSucursalAsociada().getDireccionSucursal()+"\n";
        return output;
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

}
