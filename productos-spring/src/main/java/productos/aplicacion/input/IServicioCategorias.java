package productos.aplicacion.input;

import java.util.List;

import productos.dominio.modelo.Categoria;
import productos.infraestructura.persistencia.base.EntidadNoEncontrada;
import productos.infraestructura.persistencia.base.RepositorioException;

public interface IServicioCategorias {

    void cargarJerarquia(String rutaFichero) throws RepositorioException;

    void modificarDescripcion(String idCategoria, String descripcion)
            throws RepositorioException, EntidadNoEncontrada;

    List<Categoria> recuperarCategoriasRaiz() throws RepositorioException;

    List<Categoria> recuperarDescendientes(String idCategoria)
            throws RepositorioException, EntidadNoEncontrada;
}
