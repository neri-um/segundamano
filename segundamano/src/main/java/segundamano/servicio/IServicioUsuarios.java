package segundamano.servicio;

import java.time.LocalDate;

import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

public interface IServicioUsuarios {
	/**
	 * Alta de un usuario con la información de nombre, apellidos, email, clave, fecha de nacimiento y, opcionalmente, teléfono. La aplicación proporcionará un identificador único al usuario y lo retornará. Todos los usuarios se registran como no administradores.

Modificar un usuario. Esta operación recibe como parámetro el identificador de un usuario y los datos a modificar del mismo. 
La operación debe permitir modificar el nombre, apellidos, clave, fecha de nacimiento y teléfono
	 * @throws RepositorioException 

	 */
	String altaUsuario(String nombre, String apellidos, String email, String clave, LocalDate fechaNac,
			String telefono) throws RepositorioException;
	
	void modificarUsuario(String id, String nombre, String apellidos, String clave, LocalDate fechaNac, String telefono)
			throws RepositorioException, EntidadNoEncontrada;


}
