package productos.repositorio;

import java.util.List;

import productos.modelo.Categoria;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
import repositorio.RepositorioString;

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