package segundamano.repositorio;

import java.util.List;

import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
import repositorio.RepositorioString;
import segundamano.modelo.Categoria;

/*
 * Esta interfaz extiende la definición genérica con operaciones *ad hoc* de consulta.
 * 
 * Es una interfaz concreta para una entidad (Categoria).
 */
public interface RepositorioCategoriasAdHoc extends RepositorioString<Categoria> {

    void actualizarDescripcion(String idCategoria, String descripcion)
            throws RepositorioException, EntidadNoEncontrada;

    List<Categoria> recuperarCategoriasRaiz() throws RepositorioException;

    List<Categoria> recuperarDescendientes(String idCategoria)
            throws RepositorioException, EntidadNoEncontrada;

    List<String> getIdsCategoriaYDescendientes(String idCategoria)
            throws RepositorioException, EntidadNoEncontrada;
}