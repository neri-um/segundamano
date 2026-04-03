package productos.infraestructura.adaptadores.salida;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import productos.dominio.modelo.Categoria;

@Repository
public interface RepositorioCategoriasJPA extends JpaRepository<Categoria, String> {

}
