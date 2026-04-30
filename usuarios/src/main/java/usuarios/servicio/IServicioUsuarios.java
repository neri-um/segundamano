package usuarios.servicio;

import java.time.LocalDate;
import java.util.List;

import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
import usuarios.modelo.Usuario;

public interface IServicioUsuarios {

	String altaUsuario(String nombre, String apellidos, String email, String clave, LocalDate fechaNac, String telefono)
			throws RepositorioException;

	void modificarUsuario(String id, String nombre, String apellidos, String clave, LocalDate fechaNac, String telefono)
			throws RepositorioException, EntidadNoEncontrada;

	Usuario recuperarUsuario(String id) throws RepositorioException, EntidadNoEncontrada;

	List<Usuario> listarUsuarios() throws RepositorioException;

	Usuario login(String email, String clave) throws RepositorioException, EntidadNoEncontrada;

	void incrementarVentas(String idUsuario) throws RepositorioException;

	void incrementarCompras(String idUsuario) throws RepositorioException;
	
	Usuario buscarPorGithubLogin(String githubLogin) throws RepositorioException, EntidadNoEncontrada;
}
