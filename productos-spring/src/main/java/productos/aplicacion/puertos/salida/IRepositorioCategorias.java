package productos.aplicacion.puertos.salida;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import productos.dominio.modelo.Categoria;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

@NoRepositoryBean
public interface IRepositorioCategorias extends CrudRepository<Categoria, String> {

    List<Categoria> recuperarCategoriasRaiz() throws RepositorioException;

    List<Categoria> recuperarDescendientes(String id)
        throws RepositorioException, EntidadNoEncontrada;

    List<String> getIdsCategoriaYDescendientes(String idCategoria)
        throws RepositorioException, EntidadNoEncontrada;
}