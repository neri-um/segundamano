package usuarios.servicio.test;

import java.time.LocalDate;

import usuarios.servicio.IServicioUsuarios;
import servicio.FactoriaServicios;

public class PruebaUsuarios {

    public static void main(String[] args) throws Exception {

        IServicioUsuarios servicio = FactoriaServicios.getServicio(IServicioUsuarios.class);

        // Alta de usuario
        String idUsuario = servicio.altaUsuario(
            "Juan", "García López",
            "juan@example.com", "clave123",
            LocalDate.of(2003, 5, 15), "612345678"
        );
        System.out.println("Usuario creado con ID: " + idUsuario);

        // Modificar usuario
        servicio.modificarUsuario(idUsuario, "Juan Antonio", null, null, null, null);
        System.out.println("Usuario modificado correctamente.");

        System.out.println("fin.");
    }
}