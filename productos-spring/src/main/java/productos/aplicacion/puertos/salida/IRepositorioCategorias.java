package productos.aplicacion.puertos.salida;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import productos.dominio.modelo.Categoria;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

public interface IRepositorioCategorias extends JpaRepository<Categoria, String> {

    @Query("SELECT c FROM Categoria c WHERE c.padre IS NULL")
    List<Categoria> recuperarCategoriasRaiz() throws RepositorioException;

    @Modifying
    @Transactional
    @Query("UPDATE Categoria c SET c.descripcion = :descripcion WHERE c.id = :id")
    void actualizarDescripcion(@Param("id") String id, @Param("descripcion") String descripcion) 
        throws RepositorioException, EntidadNoEncontrada;

    @Query("SELECT c FROM Categoria c WHERE c.padre.id = :id")
    List<Categoria> recuperarDescendientes(@Param("id") String id) 
        throws RepositorioException, EntidadNoEncontrada;

    @Query("SELECT c.id FROM Categoria c WHERE c.id = :idCategoria OR c.padre.id = :idCategoria")
    List<String> getIdsCategoriaYDescendientes(@Param("idCategoria") String idCategoria) 
        throws RepositorioException, EntidadNoEncontrada;
}