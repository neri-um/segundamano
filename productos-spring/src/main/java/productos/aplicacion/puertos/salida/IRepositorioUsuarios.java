package productos.aplicacion.puertos.salida;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import productos.dominio.modelo.UsuarioSimplificado;

@NoRepositoryBean
public interface IRepositorioUsuarios extends CrudRepository<UsuarioSimplificado, String> {
    // Sin operaciones ad hoc necesarias
}