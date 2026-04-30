package productos.infraestructura.adaptadores.salida;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import productos.aplicacion.puertos.salida.IRepositorioCategorias;
import productos.dominio.modelo.Categoria;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

@Repository
public interface RepositorioCategoriasJPA
        extends IRepositorioCategorias, JpaRepository<Categoria, String> {

    @Override
    @Query("SELECT c FROM Categoria c WHERE c.padre IS NULL")
    List<Categoria> recuperarCategoriasRaiz() throws RepositorioException;

    @Override
    @Query("SELECT c FROM Categoria c WHERE c.padre.id = :id")
    List<Categoria> recuperarDescendientes(@Param("id") String id)
        throws RepositorioException, EntidadNoEncontrada;

    @Override
    @Query("SELECT c.id FROM Categoria c WHERE c.id = :idCategoria OR c.padre.id = :idCategoria")
    List<String> getIdsCategoriaYDescendientes(@Param("idCategoria") String idCategoria)
        throws RepositorioException, EntidadNoEncontrada;
}