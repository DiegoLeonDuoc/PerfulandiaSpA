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

    public String deleteEmpleado(int rut) {
        empleadoRepository.deleteById(rut);
        return "Empleado eliminado con éxito";
    }

    public String updateEmpleado(Empleado empleado, int rut) {
        if (empleadoRepository.existsById(rut)) {
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
        output+="RUT: "+empleado.getRutUs()+"-"+empleado.getDvUs()+"\n";
        output+="Nombre completo: "+empleado.getNomUs()+" ";
        if (empleado.getNom2Us() != null) {
            output+=empleado.getNom2Us()+" ";
        }
        output+=empleado.getApPat();
        if (empleado.getNom2Us() != null) {
            output+=" "+empleado.getApMat()+"\n";
        } else {
            output+="\n";
        }
        output+="Sexo: "+empleado.getSexoUs()+"\n";
        output+="Fecha de nacimiento: "+empleado.getFecNac().toString()+"\n";
        output+="Dirección: "+empleado.getDirUs()+"\n";
        output+="Número de teléfono: +569"+empleado.getTel1Us()+"\n";
        if (empleado.getTel2Us() != null) {
            output+="Teléfono extra: +569"+empleado.getTel2Us()+"\n";
        }
        output+="Email: "+empleado.getEmailUs()+"\n";
        output+="ID Sucursal: "+empleado.getSucursal().getId()+"\n";
        output+="Nombre Sucursal: "+empleado.getSucursal().getNomSuc()+"\n";
        output+="Dirección Sucursal: "+empleado.getSucursal().getDirSuc()+"\n";
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
