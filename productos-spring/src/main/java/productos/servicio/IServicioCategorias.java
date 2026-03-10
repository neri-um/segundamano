package productos.servicio;

import java.util.List;

import productos.modelo.Categoria;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

public interface IServicioCategorias {

    void cargarJerarquia(String rutaFichero) throws RepositorioException;

    void modificarDescripcion(String idCategoria, String descripcion)
            throws RepositorioException, EntidadNoEncontrada;

    List<Categoria> recuperarCategoriasRaiz() throws RepositorioException;

    List<Categoria> recuperarDescendientes(String idCategoria)
            throws RepositorioException, EntidadNoEncontrada;
}
