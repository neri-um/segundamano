package usuarios.repositorio;

import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
import repositorio.RepositorioString;
import usuarios.modelo.Usuario;

/*
 * Esta interfaz extiende la definición genérica con operaciones *ad hoc* de consulta.
 * 
 * Es una interfaz concreta para una entidad (Usuarios).
 */
public interface RepositorioUsuariosAdHoc extends RepositorioString<Usuario> {

    Usuario getByEmail(String email) throws EntidadNoEncontrada, RepositorioException;
    
    Usuario getByGithubLogin(String githubLogin) throws EntidadNoEncontrada, RepositorioException;
}